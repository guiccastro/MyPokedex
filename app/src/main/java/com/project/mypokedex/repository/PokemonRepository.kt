package com.project.mypokedex.repository

import android.content.Context
import android.util.Log
import com.project.mypokedex.database.dao.PokemonDao
import com.project.mypokedex.extensions.getIDFromURL
import com.project.mypokedex.getBasicKeysPreferences
import com.project.mypokedex.getTotalPokemonsPreferences
import com.project.mypokedex.model.EvolutionChain
import com.project.mypokedex.model.EvolutionChainItem
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.PokemonColor
import com.project.mypokedex.model.PokemonGeneration
import com.project.mypokedex.model.PokemonSpecies
import com.project.mypokedex.model.PokemonType
import com.project.mypokedex.model.downloadInfo.DownloadInfoType
import com.project.mypokedex.model.downloadInfo.RequestUpdateClass
import com.project.mypokedex.model.downloadInfo.UpdateInfo
import com.project.mypokedex.network.responses.BasicKeysResponse
import com.project.mypokedex.network.responses.BasicResponse
import com.project.mypokedex.network.responses.EvolutionChainResponse
import com.project.mypokedex.network.responses.PokemonResponse
import com.project.mypokedex.network.responses.PokemonSpeciesEvolutionChainResponse
import com.project.mypokedex.network.responses.PokemonSpeciesResponse
import com.project.mypokedex.network.responses.PokemonSpeciesVarietiesResponse
import com.project.mypokedex.network.services.EvolutionChainService
import com.project.mypokedex.network.services.PokemonService
import com.project.mypokedex.network.services.PokemonSpeciesService
import com.project.mypokedex.saveBasicKeysPreferences
import com.project.mypokedex.saveTotalPokemonsPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val dao: PokemonDao,
    private val pokemonClient: PokemonService,
    private val evolutionChainClient: EvolutionChainService,
    private val pokemonSpeciesClient: PokemonSpeciesService,
    val context: Context
): RequestUpdateClass {
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
    var needToRequestPokemons: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var pokemonDownloadInfo: DownloadInfoType = DownloadInfoType.None()

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

    private suspend fun verifyDaoData() {
        if (needToRequestBasicKeys()) {
            getBasicKeys()
        } else {
            Log.i(TAG, "verifyDaoData: Basic Keys read from DAO")
        }

        pokemonDownloadInfo =
            DownloadInfoType.getPokemonDownloadInfo(pokemonList.value, totalPokemons)
        needToRequestPokemons.value = pokemonDownloadInfo != DownloadInfoType.None()

        if (!needToRequestPokemons.value) {
            Log.i(TAG, "verifyDaoData: Pokemon List read from DAO")
            totalPokemons = pokemonList.value.size
            progressRequest.value = 1F
        }
    }

    suspend fun prepareToRequestPokemons() {
        setRequestPokemons()
        requestAllPokemons()
    }

    private fun needToRequestBasicKeys(): Boolean =
        pokemonBasicKeyID.isEmpty() || pokemonBasicKeyName.isEmpty() || totalPokemons == 0

    private fun setRequestPokemons() {
        requestPokemons.addAll(
            pokemonDownloadInfo.getRequestPokemons(
                pokemonBasicKeyID.keys.toList(),
                pokemonList.value
            )
        )
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

    private fun setBasicKeys(keyList: List<Pair<String, Int>>) {
        pokemonBasicKeyID = keyList.associate { it.second to it.first }
        pokemonBasicKeyName = keyList.associate { it.first to it.second }
    }

    override suspend fun requestInfo(id: Int) {
        if (pokemonDownloadInfo == DownloadInfoType.FullInfo()) {
            requestPokemonFull(id)
        } else if (pokemonDownloadInfo == DownloadInfoType.NewInfo(emptyList())) {
            requestOnlyPokemonInfo(id)
        }
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
                        requestInfo(id)
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

    private fun calculateProgressRequest() {
        progressRequest.value =
            1F - (requestPokemons.size.toFloat() / totalPokemons.toFloat())//pokemonList.value.size.toFloat() / totalPokemons.toFloat()
        Log.i(TAG, "ProgressRequest: ${progressRequest.value}%")
    }

    suspend fun getPokemon(id: Int): Pokemon? {
        return pokemonList.value.find {
            it.id == id
        } ?: withContext(IO) {
            Log.i(TAG, "getPokemon: Getting pokemon $id from DAO")
            return@withContext dao.getById(id)
        }
    }

    private suspend fun requestOnlyPokemonInfo(id: Int) {
        try {
            Log.i(TAG, "requestOnlyPokemonInfo: Requesting pokemon $id from client")
            val pokemonResponse = pokemonClient.getPokemon(id)
            Log.i(TAG, "requestOnlyPokemonInfo: Get pokemon $id from client")
            val newPokemon = createPokemon(pokemonResponse)
            val oldPokemon = pokemonList.value.first { it.id == id }
            newPokemon.apply {
                species = oldPokemon.species
            }
            val listCopy = pokemonList.value.toMutableList()
            listCopy.removeIf { it.id == id }
            pokemonList.value = (listCopy + newPokemon)

            CoroutineScope(IO).launch {
                dao.insert(newPokemon)
            }
        } catch (e: Exception) {
            Log.i(TAG, "requestOnlyPokemonInfo: Failure on requesting pokemon $id - $e")
            requestOnlyPokemonInfo(id)
        }
    }

    private suspend fun requestPokemonFull(id: Int) {
        try {
            Log.i(TAG, "requestPokemonFull: Requesting pokemon $id from client")
            val pokemonResponse = pokemonClient.getPokemon(id)
            Log.i(TAG, "requestPokemonFull: Get pokemon $id from client")
            val pokemon = createPokemon(pokemonResponse).apply {
                species = getPokemonSpecies(speciesId)
            }
            pokemonList.value = (pokemonList.value + pokemon)

            CoroutineScope(IO).launch {
                dao.insert(pokemon)
            }
        } catch (e: Exception) {
            Log.i(TAG, "requestPokemonFull: Failure on requesting pokemon $id - $e")
            requestPokemonFull(id)
        }
    }

    private suspend fun getPokemonSpecies(id: Int): PokemonSpecies {
        return try {
            Log.i(TAG, "getPokemonSpeciesNew: Requesting pokemon species $id from client")
            val pokemonSpeciesResponse = pokemonSpeciesClient.getPokemonSpecies(id)
            Log.i(TAG, "getPokemonSpeciesNew: Get pokemon species $id from client")
            createPokemonSpecies(pokemonSpeciesResponse)
        } catch (e: Exception) {
            Log.i(TAG, "getPokemonSpeciesNew: Failure on requesting pokemon species $id - $e")
            getPokemonSpecies(id)
        }

    }

    private fun createPokemon(pokemonResponse: PokemonResponse): Pokemon {
        Log.i(TAG, "createPokemon: Creating pokemon")
        val id = pokemonResponse.id
        val name = pokemonResponse.name
        val types = pokemonResponse.types.mapNotNull {
            PokemonType.fromId(
                it.type.url.getIDFromURL()
            )
        }
        val species = pokemonResponse.species.url.getIDFromURL()
        val sprites = pokemonResponse.sprites
        val height = pokemonResponse.height
        val weight = pokemonResponse.weight

        val newPokemon = Pokemon(id, name, types, species, sprites, height)
        Log.i(TAG, "createPokemon: Pokemon created $newPokemon")

        return newPokemon
    }

    private suspend fun createPokemonSpecies(pokemonSpeciesResponse: PokemonSpeciesResponse): PokemonSpecies {
        Log.i(TAG, "createPokemonSpecies: Creating pokemon species")
        var evolutionChain: EvolutionChain = EvolutionChain(EvolutionChainItem(0, emptyList()))
        var varieties: List<Int> = emptyList()
        var generation: PokemonGeneration = PokemonGeneration.Unknown
        var color: PokemonColor = PokemonColor.Black
        CoroutineScope(Main).launch {
            async {
                evolutionChain = createEvolutionChain(pokemonSpeciesResponse.evolutionChain)
            }
            async {
                varieties = createPokemonVarieties(pokemonSpeciesResponse.varieties)
            }
            async {
                generation = createPokemonGeneration(pokemonSpeciesResponse.generation)
            }
            async {
                color = createPokemonColor(pokemonSpeciesResponse.color)
            }
        }.join()
        Log.i(TAG, "createPokemonSpecies: Pokemon species created")

        return PokemonSpecies(evolutionChain, varieties, generation, color)
    }

    private suspend fun createEvolutionChain(evolutionChainSpeciesResponse: PokemonSpeciesEvolutionChainResponse): EvolutionChain {
        return try {
            Log.i(TAG, "createEvolutionChain: Creating evolution chain")
            val evolutionChainId = evolutionChainSpeciesResponse.url.getIDFromURL()
            val evolutionChainResponse =
                evolutionChainClient.getEvolutionChain(evolutionChainId).chain
            val evolutionChainBaseItem = createEvolutionChainItem(evolutionChainResponse)
            Log.i(TAG, "createEvolutionChain: Evolution chain created")

            EvolutionChain(evolutionChainBaseItem)
        } catch (e: Exception) {
            Log.i(TAG, "createEvolutionChain: Failure on creating evolution chain - $e")
            createEvolutionChain(evolutionChainSpeciesResponse)
        }

    }

    private fun createEvolutionChainItem(chainResponse: EvolutionChainResponse): EvolutionChainItem {
        return EvolutionChainItem(
            pokemonId = chainResponse.species.url.getIDFromURL(),
            evolvesTo = chainResponse.evolvesToList.map { createEvolutionChainItem(it) }
        )
    }

    private fun createPokemonVarieties(varietiesResponse: List<PokemonSpeciesVarietiesResponse>): List<Int> {
        return varietiesResponse.map { it.pokemon.url.getIDFromURL() }
    }

    private fun createPokemonGeneration(generationResponse: BasicResponse): PokemonGeneration {
        return PokemonGeneration.fromId(generationResponse.url.getIDFromURL())
    }

    private fun createPokemonColor(colorResponse: BasicResponse): PokemonColor {
        return PokemonColor.fromId(colorResponse.url.getIDFromURL())
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
}