package com.project.mypokedex.repository

import android.util.Log
import com.project.mypokedex.client.CircuitBreakerConfiguration
import com.project.mypokedex.client.PokemonClient
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.PokemonType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PokemonRepository {

    private const val REQUESTS_AT_A_TIME = 20
    private const val MAX_BASIC_KEY_RETRY = 5

    private val TAG = PokemonRepository::class.java.simpleName

    private val client = PokemonClient(CircuitBreakerConfiguration()).getClient()

    private var basicKeyOnFailure = 0
    private var pokemonBasicKeyID: Map<Int, String> = emptyMap()
    private var pokemonBasicKeyName: Map<String, Int> = emptyMap()
    private var totalPokemons = 0

    private val requestPokemons = ArrayList<Int>()

    val pokemonList: MutableStateFlow<List<Pokemon>> = MutableStateFlow(emptyList())

    var progressRequest = 0


    init {
        getBasicKeys()
    }

    private fun getBasicKeys() {
        Log.i(TAG, "getBasicKeys: Requesting Basic Keys")
        client.getBasicKeys().enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    response.body()?.let {
                        val jsonObj = Json.parseToJsonElement(it).jsonObject
                        parseBasicKeys(jsonObj)
                        request()
                        Log.i(TAG, "onResponse: Basic Keys")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    basicKeyOnFailure++
                    Log.i(TAG, "onFailure: Basic Keys - Failures: $basicKeyOnFailure")
                    if (basicKeyOnFailure == MAX_BASIC_KEY_RETRY) {
                        getBasicKeys()
                    }
                }

            }
        )
    }

    private fun parseBasicKeys(info: JsonObject) {
        totalPokemons = info["count"]?.jsonPrimitive?.content?.toIntOrNull() ?: 0

        val keyList = ArrayList<Pair<String, Int>>()
        info["results"]?.jsonArray?.forEach {
            val name = it.jsonObject["name"]?.jsonPrimitive?.content ?: return
            val url = it.jsonObject["url"]?.jsonPrimitive?.content ?: return
            val id = url.split("/").dropLast(1).last().toInt()

            keyList.add(Pair(name, id))
        }
        pokemonBasicKeyID = keyList.associate { it.second to it.first }
        pokemonBasicKeyName = keyList.associate { it.first to it.second }

        requestPokemons.addAll(pokemonBasicKeyID.keys)
    }

    private fun request() {
        var responseCount = 0
        val toIndex = if (requestPokemons.size >= REQUESTS_AT_A_TIME) {
            REQUESTS_AT_A_TIME
        } else {
            requestPokemons.size
        }
        requestPokemons.subList(0, toIndex).forEach { id ->
            Log.i(TAG, "requestPokemon: $id")
            client.getPokemon(id).enqueue(
                object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        response.body()?.let {
                            val jsonObj = Json.parseToJsonElement(it).jsonObject
                            parseAndSave(jsonObj)
                            calculateProgressRequest()
                            requestPokemons.remove(id)
                            responseCount++
                            if (responseCount == toIndex) {
                                request()
                            }
                            Log.i(TAG, "onResponse: Pokemon - $id")

                            if (requestPokemons.isEmpty()) {
                                pokemonList.value =
                                    pokemonList.value.sortedBy { pokemon -> pokemon.id }
                                Log.i(TAG, "onResponse: All Pokemons requested correctly!")
                            }
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.i(TAG, "onFailure: Pokemon - $id - $t")
                        responseCount++
                        if (responseCount == toIndex) {
                            request()
                        }
                    }
                }
            )
        }
    }

    private fun parseAndSave(info: JsonObject) {
        val id = info["id"]?.jsonPrimitive?.content?.toInt()
        val name = info["name"]?.jsonPrimitive?.content
        val types = info["types"]?.jsonArray?.mapNotNull {
            it.jsonObject["type"]?.jsonObject?.get("name")?.jsonPrimitive?.contentOrNull?.let { typeName ->
                PokemonType.fromName(typeName)
            }
        } ?: emptyList()
        val image =
            info["sprites"]?.jsonObject?.get("front_default")?.jsonPrimitive?.contentOrNull ?: ""
        val gif =
            info["sprites"]?.jsonObject?.get("versions")?.jsonObject?.get("generation-v")?.jsonObject?.get(
                "black-white"
            )?.jsonObject?.get("animated")?.jsonObject?.get("front_default")?.jsonPrimitive?.contentOrNull
                ?: ""

        if (id != null &&
            name != null
        ) {
            val newPokemon = Pokemon(id, name, types, image, gif)
            Log.i(TAG, "parseAndSave: $newPokemon")
            pokemonList.value = (pokemonList.value + newPokemon)
        }
    }

    private fun calculateProgressRequest() {
        progressRequest = (pokemonList.value.size * 100) / totalPokemons
        Log.i(TAG, "ProgressRequest: $progressRequest%")
    }

    fun searchBasicKey(name: String) {
        pokemonBasicKeyName.keys.forEach { nameKey ->
            if (nameKey.contains(name)) {
                pokemonBasicKeyName[nameKey]?.let { id ->
                    getPokemon(id)
                }
            }
        }
    }

    fun getPokemon(id: Int): Pokemon? {
        return pokemonList.value.find {
            it.id == id
        }
    }

    fun getPokemon(name: String): Pokemon? {
        return pokemonList.value.find {
            it.name == name
        }
    }
}