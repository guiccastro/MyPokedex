package com.project.mypokedex.repository

import android.content.Context
import android.util.Log
import com.project.mypokedex.database.dao.PokemonDao
import com.project.mypokedex.extensions.getIDFromURL
import com.project.mypokedex.getBasicKeysPreferences
import com.project.mypokedex.getTotalPokemonsPreferences
import com.project.mypokedex.model.DownloaderInfo
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.network.responses.BasicKeysResponse
import com.project.mypokedex.network.services.EvolutionChainService
import com.project.mypokedex.network.services.PokemonService
import com.project.mypokedex.network.services.PokemonSpeciesService
import com.project.mypokedex.saveBasicKeysPreferences
import com.project.mypokedex.saveTotalPokemonsPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val dao: PokemonDao,
    pokemonClient: PokemonService,
    evolutionChainClient: EvolutionChainService,
    pokemonSpeciesClient: PokemonSpeciesService,
    val context: Context
) {
    companion object {
        private const val TAG = "PokemonRepository"
    }

    val downloaderInfo =
        DownloaderInfo(this, pokemonClient, evolutionChainClient, pokemonSpeciesClient)

    private var pokemonBasicKeyID: Map<Int, String> = emptyMap()
    private var pokemonBasicKeyName: Map<String, Int> = emptyMap()
    private var totalPokemons = 0
    val pokemonList: MutableStateFlow<List<Pokemon>> = MutableStateFlow(emptyList())

    init {
        CoroutineScope(Main).launch {
            runBlocking(IO) {
                pokemonList.value = dao.getAll()
                setBasicKeys(getBasicKeysPreferences(context).first())
                totalPokemons = getTotalPokemonsPreferences(context).first()
            }

            verifyDaoData()

            downloaderInfo.progressRequest.collect { progressRequest ->
                if (progressRequest == 1F) {
                    pokemonList.value = pokemonList.value.sortedBy { it.id }
                    Log.i(TAG, "onResponse: All Pokemons requested correctly!")
                }
            }
        }
    }

    private suspend fun verifyDaoData() {
        if (needToRequestBasicKeys()) {
            parseAndSaveBasicKeysResponse(downloaderInfo.requestBasicKeys())
        } else {
            Log.i(TAG, "verifyDaoData: Basic Keys read from DAO")
        }

        downloaderInfo.prepareToDownload(pokemonList.value, totalPokemons)
    }

    private fun needToRequestBasicKeys(): Boolean =
        pokemonBasicKeyID.isEmpty() || pokemonBasicKeyName.isEmpty() || totalPokemons == 0

    private fun parseAndSaveBasicKeysResponse(basicKeys: BasicKeysResponse?) {
        if (basicKeys == null) return
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

    fun getBasicKeys(): List<Int> {
        return pokemonBasicKeyID.keys.toList()
    }

    fun getTotalPokemons(): Int {
        return totalPokemons
    }

    fun addPokemon(pokemon: Pokemon) {
        pokemonList.value = (pokemonList.value + pokemon).sortedBy { it.id }

        CoroutineScope(IO).launch {
            dao.insert(pokemon)
        }
    }

    fun replacePokemon(pokemon: Pokemon) {
        val listCopy = pokemonList.value.toMutableList()
        listCopy.removeIf { it.id == pokemon.id }
        pokemonList.value = (listCopy + pokemon)

        CoroutineScope(IO).launch {
            dao.insert(pokemon)
        }
    }

    suspend fun getPokemon(id: Int): Pokemon? {
        return pokemonList.value.find {
            it.id == id
        } ?: withContext(IO) {
            Log.i(TAG, "getPokemon: Getting pokemon $id from DAO")
            return@withContext dao.getById(id)
        }
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