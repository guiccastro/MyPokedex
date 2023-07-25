package com.project.mypokedex.repository

import android.content.Context
import android.util.Log
import com.project.mypokedex.database.dao.PokemonDao
import com.project.mypokedex.getBasicKeysPreferences
import com.project.mypokedex.getTotalPokemonsPreferences
import com.project.mypokedex.model.EvolutionChain
import com.project.mypokedex.model.EvolutionChainItem
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.PokemonSpecies
import com.project.mypokedex.model.PokemonType
import com.project.mypokedex.network.responses.BasicKeysResponse
import com.project.mypokedex.network.responses.EvolutionChainResponse
import com.project.mypokedex.network.responses.PokemonResponse
import com.project.mypokedex.network.services.EvolutionChainService
import com.project.mypokedex.network.services.PokemonService
import com.project.mypokedex.network.services.PokemonSpeciesService
import com.project.mypokedex.saveBasicKeysPreferences
import com.project.mypokedex.saveTotalPokemonsPreferences
import com.project.mypokedex.ui.extensions.getIDFromURL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val dao: PokemonDao,
    private val pokemonClient: PokemonService,
    private val evolutionChainClient: EvolutionChainService,
    private val pokemonSpeciesClient: PokemonSpeciesService,
    private val context: Context
) {
    companion object {
        private const val MAX_BASIC_KEY_RETRY = 5
        private const val TAG = "PokemonRepository"
    }

    private var pokemonBasicKeyID: Map<Int, String> = emptyMap()
    private var pokemonBasicKeyName: Map<String, Int> = emptyMap()
    private var totalPokemons = 0

    private val requestPokemons = ArrayList<Int>()

    val pokemonList: MutableStateFlow<List<Pokemon>> = MutableStateFlow(emptyList())
    var progressRequest: MutableStateFlow<Float> = MutableStateFlow(0F)

    init {
        CoroutineScope(Main).launch {
            runBlocking(IO) {
                pokemonList.value = dao.getAll()
                setBasicKeys(getBasicKeysPreferences(context).first())
                totalPokemons = getTotalPokemonsPreferences(context).first()
            }

            verifyDaoData()
        }
    }

    private suspend fun getPokemonSpecies(id: Int): PokemonSpecies {
        return try {
            Log.i(TAG, "getPokemonSpecies: $id")
            val pokemonSpeciesResponse = pokemonSpeciesClient.getPokemonSpecies(id)
            Log.i(TAG, "onResponse: PokemonSpecies - $id")

            PokemonSpecies(
                varieties = pokemonSpeciesResponse.varieties.mapNotNull {
                    getPokemon(it.pokemon.url.getIDFromURL())
                }
            )
        } catch (e: Exception) {
            Log.i(TAG, "onFailure: PokemonSpecies - $id - $e")
            PokemonSpecies(emptyList())
        }
    }

    suspend fun getEvolutionChainByPokemon(pokemon: Pokemon): EvolutionChain {
        try {
            val pokemonSpecies = pokemonSpeciesClient.getPokemonSpecies(pokemon.id)
            val evolutionChainID =
                pokemonSpecies.evolutionChain.url.getIDFromURL()
            val evolutionChainResponse =
                evolutionChainClient.getEvolutionChain(evolutionChainID).chain

            return EvolutionChain(
                chain = createEvolutionChain(evolutionChainResponse) ?: EvolutionChainItem(
                    pokemon,
                    emptyList()
                )
            )
        } catch (e: HttpException) {
            return EvolutionChain(
                chain = EvolutionChainItem(
                    pokemon,
                    emptyList()
                )
            )
        }

    }

    private fun createEvolutionChain(chainResponse: EvolutionChainResponse): EvolutionChainItem? {
        return getPokemon(chainResponse.species.url.getIDFromURL())?.let { pokemon ->
            EvolutionChainItem(
                pokemon = pokemon,
                evolvesTo = chainResponse.evolvesToList.mapNotNull { createEvolutionChain(it) }
            )
        }
    }

    private fun setBasicKeys(keyList: List<Pair<String, Int>>) {
        pokemonBasicKeyID = keyList.associate { it.second to it.first }
        pokemonBasicKeyName = keyList.associate { it.first to it.second }
    }

    private suspend fun verifyDaoData() {
        if (needToRequestBasicKeys()) {
            getBasicKeys()
        } else {
            Log.i(TAG, "verifyDaoData: Basic Keys read from DAO")
        }

        if (needToRequestPokemon()) {
            setRequestPokemons()
            requestAllPokemons()
        } else {
            Log.i(TAG, "verifyDaoData: Pokemon List read from DAO")
            totalPokemons = pokemonList.value.size
            progressRequest.value = 1F
        }
    }

    private fun needToRequestBasicKeys(): Boolean =
        pokemonBasicKeyID.isEmpty() || pokemonBasicKeyName.isEmpty() || totalPokemons == 0

    private fun needToRequestPokemon(): Boolean =
        pokemonList.value.isEmpty() || pokemonList.value.size != totalPokemons

    private fun setRequestPokemons() {
        pokemonBasicKeyID.keys.forEach { keyID ->
            if (pokemonList.value.indexOfFirst { it.id == keyID } == -1) {
                requestPokemons.add(keyID)
            }
        }
    }

    private suspend fun getBasicKeys() {
        var onFailureCount = 0

        try {
            Log.i(TAG, "getBasicKeys: Requesting Basic Keys")
            val basicKeys = pokemonClient.getBasicKeys()
            Log.i(TAG, "onResponse: Basic Keys")

            parseAndSaveBasicKeysResponse(basicKeys)
        } catch (e: Exception) {
            onFailureCount++
            Log.i(TAG, "onFailure: Basic Keys - Failures: $onFailureCount - $e")
            if (onFailureCount < MAX_BASIC_KEY_RETRY) {
                getBasicKeys()
            }
        }
    }

    private fun parseAndSaveBasicKeysResponse(basicKeys: BasicKeysResponse) {
        totalPokemons = basicKeys.count

        val keyList = ArrayList<Pair<String, Int>>()
        basicKeys.results.forEach { keyResponse ->
            val name = keyResponse.name
            val id = keyResponse.url.getIDFromURL()
            keyList.add(Pair(name, id))
        }

        CoroutineScope(IO).launch {
            saveBasicKeysPreferences(context, keyList)
            saveTotalPokemonsPreferences(context, totalPokemons)
        }

        setBasicKeys(keyList)
    }

    private suspend fun requestAllPokemons() {
        var responseCount = 0
        val toIndex = if (requestPokemons.size >= totalPokemons) {
            totalPokemons
        } else {
            requestPokemons.size
        }
        CoroutineScope(Main).launch {
            requestPokemons.subList(0, toIndex).forEach { id ->
                async {
                    try {
                        Log.i(TAG, "requestPokemon: $id")
                        val pokemon = pokemonClient.getPokemon(id)
                        Log.i(TAG, "onResponse: Pokemon - $id")

                        parseAndSavePokemonResponse(pokemon)
                        requestPokemons.remove(id)

                        calculateProgressRequest()

                        responseCount++
                    } catch (e: Exception) {
                        Log.i(TAG, "onFailure: Pokemon - $id - $e")
                    }
                }
            }
        }.join()

        if (progressRequest.value == 1F) {
            pokemonList.value = pokemonList.value.sortedBy { it.id }
            Log.i(TAG, "onResponse: All Pokemons requested correctly!")
        } else {
            requestAllPokemons()
        }

    }

    private fun parseAndSavePokemonResponse(info: PokemonResponse) {
        val id = info.id
        val name = info.name
        val types = info.types.mapNotNull {
            PokemonType.fromId(
                it.type.url.getIDFromURL()
            )
        }
        val species = info.species.url.getIDFromURL()
        val sprites = info.sprites

        val newPokemon = Pokemon(id, name, types, species, sprites)
        Log.i(TAG, "parseAndSave: $newPokemon")
        pokemonList.value = (pokemonList.value + newPokemon)

        CoroutineScope(IO).launch {
            dao.insert(newPokemon)
        }
    }

    private fun calculateProgressRequest() {
        progressRequest.value = pokemonList.value.size.toFloat() / totalPokemons.toFloat()
        Log.i(TAG, "ProgressRequest: ${progressRequest.value}%")
    }

    fun getPokemon(id: Int): Pokemon? {
        return pokemonList.value.find {
            it.id == id
        } ?: dao.getById(id)
    }

    suspend fun getRandomPokemons(quantity: Int): List<Pokemon> {
        if (totalPokemons == 0) return emptyList()
        val idList = 1.rangeTo(totalPokemons).toMutableList()
        val selectedIds = ArrayList<Int>()

        repeat(quantity) {
            val id = idList.toIntArray().random()
            selectedIds.add(id)
            idList.remove(id)
        }

        val selectedPokemons = ArrayList<Pokemon>()
        selectedIds.forEach { id ->
            withContext(IO) {
                getPokemon(id)?.let { pokemon ->
                    selectedPokemons.add(pokemon)
                }
            }
        }

        if (selectedPokemons.size != quantity) {
            selectedPokemons.addAll(getRandomPokemons(selectedPokemons.size - quantity))
        }

        return selectedPokemons
    }

    suspend fun getSpecies(pokemon: Pokemon): PokemonSpecies {
        return pokemon.species ?: run {
            val species = getPokemonSpecies(pokemon.speciesId)
            pokemon.species = species
            species
        }
    }
}