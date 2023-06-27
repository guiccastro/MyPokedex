package com.project.mypokedex.repository

import android.util.Log
import com.project.mypokedex.database.dao.PokemonDao
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.PokemonType
import com.project.mypokedex.network.responses.BasicKeysResponse
import com.project.mypokedex.network.responses.PokemonResponse
import com.project.mypokedex.network.services.PokemonService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val dao: PokemonDao,
    private val client: PokemonService
) {
    companion object {
        private const val REQUESTS_AT_A_TIME = 100
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
        CoroutineScope(IO).launch {
            pokemonList.value = dao.getAll()

            if (pokemonList.value.isEmpty()) {
                getBasicKeys()
            } else {
                progressRequest.value = 1F
            }
        }
    }

    private suspend fun getBasicKeys() {
        var onFailureCount = 0

        try {
            Log.i(TAG, "getBasicKeys: Requesting Basic Keys")
            val basicKeys = client.getBasicKeys()
            Log.i(TAG, "onResponse: Basic Keys")

            parseBasicKeys(basicKeys)
            requestAllPokemons()
        } catch (e: Exception) {
            onFailureCount++
            Log.i(TAG, "onFailure: Basic Keys - Failures: $onFailureCount - $e")
            if (onFailureCount < MAX_BASIC_KEY_RETRY) {
                getBasicKeys()
            }
        }
    }

    private fun parseBasicKeys(basicKeys: BasicKeysResponse) {
        totalPokemons = basicKeys.count

        val keyList = ArrayList<Pair<String, Int>>()
        basicKeys.results.forEach { keyResponse ->
            val name = keyResponse.name
            val id = keyResponse.url.split("/").dropLast(1).last().toInt()
            keyList.add(Pair(name, id))
        }

        pokemonBasicKeyID = keyList.associate { it.second to it.first }
        pokemonBasicKeyName = keyList.associate { it.first to it.second }

        requestPokemons.addAll(pokemonBasicKeyID.keys)
    }

    private suspend fun requestAllPokemons() {
        var responseCount = 0
        val toIndex = if (requestPokemons.size >= REQUESTS_AT_A_TIME) {
            REQUESTS_AT_A_TIME
        } else {
            requestPokemons.size
        }

        requestPokemons.subList(0, toIndex).forEach { id ->
            CoroutineScope(IO).launch {
                try {
                    Log.i(TAG, "requestPokemon: $id")
                    val pokemon = client.getPokemon(id)
                    parseAndSavePokemon(pokemon)
                    calculateProgressRequest()
                    requestPokemons.remove(id)
                    responseCount++
                    if (responseCount == toIndex) {
                        requestAllPokemons()
                    }
                    Log.i(TAG, "onResponse: Pokemon - $id")

                    if (requestPokemons.isEmpty()) {
                        pokemonList.value = pokemonList.value.sortedBy { it.id }
                        Log.i(TAG, "onResponse: All Pokemons requested correctly!")
                    }
                } catch (e: Exception) {
                    Log.i(TAG, "onFailure: Pokemon - $id - $e")
                    responseCount++
                    if (responseCount == toIndex) {
                        requestAllPokemons()
                    }
                }
            }
        }
    }

    private fun parseAndSavePokemon(info: PokemonResponse) {
        val id = info.id
        val name = info.name
        val types = info.types.mapNotNull {
            PokemonType.fromId(
                it.type.url.split("/").dropLast(1).last().toInt()
            )
        }
        val image = info.sprites.frontDefault ?: ""
        val gif = info.sprites.versions?.generationV?.blackWhite?.animated?.frontDefault ?: ""

        val newPokemon = Pokemon(id, name, types, image, gif)
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
}