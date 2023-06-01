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

    private val client = PokemonClient(CircuitBreakerConfiguration()).getClient()

    val pokemonList: MutableStateFlow<List<Pokemon>> = MutableStateFlow(emptyList())
    val isRequesting: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private var pokemonBasicKeyID: Map<Int, String> = emptyMap()
    private var pokemonBasicKeyName: Map<String, Int> = emptyMap()

    private val requestedByIDs: HashMap<Int, Boolean> = HashMap()
    private val requestedByNames: HashMap<String, Boolean> = HashMap()

    private val TAG = PokemonRepository::class.java.simpleName

    init {
        getBasicKeys()
    }

    private fun getBasicKeys() {
        client.getBasicKeys().enqueue(
            object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    response.body()?.let {
                        val jsonObj = Json.parseToJsonElement(it).jsonObject
                        parseBasicKeys(jsonObj)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            }
        )
    }

    private fun parseBasicKeys(info: JsonObject) {
        val keyList = ArrayList<Pair<String, Int>>()
        info["results"]?.jsonArray?.forEach {
            val name = it.jsonObject["name"]?.jsonPrimitive?.content ?: return
            val url = it.jsonObject["url"]?.jsonPrimitive?.content ?: return
            val id = url.split("/").dropLast(1).last().toInt()

            keyList.add(Pair(name, id))
        }
        pokemonBasicKeyID = keyList.associate { it.second to it.first }
        pokemonBasicKeyName = keyList.associate { it.first to it.second }
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

    private fun isAlreadyRequested(id: Int): Boolean {
        requestedByIDs[id]?.let {
            return true
        }
        return false
    }

    private fun isAlreadyRequested(name: String): Boolean {
        requestedByNames[name]?.let {
            return true
        }
        return false
    }

    private fun requestPokemon(id: Int) {
        if (isAlreadyRequested(id)) return
        requestedByIDs[id] = true

        Log.i(TAG, "requestPokemon: $id")
        isRequesting.value = true
        client.getPokemon(id).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let { pokemon ->
                    val jsonObj = Json.parseToJsonElement(pokemon).jsonObject
                    parseAndSave(jsonObj)
                    requestedByIDs.remove(id)
                    isRequesting.value = false
                    Log.i(TAG, "onResponse: $id")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                requestedByIDs.remove(id)
                isRequesting.value = false
                Log.i(TAG, "onFailure: $id - $t")
            }
        })
    }

    private fun requestPokemon(name: String) {
        if (isAlreadyRequested(name)) return
        requestedByNames[name] = true

        Log.i(TAG, "requestPokemon: $name")
        isRequesting.value = true
        client.getPokemon(name).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let { pokemon ->
                    val jsonObj = Json.parseToJsonElement(pokemon).jsonObject
                    parseAndSave(jsonObj)
                    requestedByNames.remove(name)
                    isRequesting.value = false
                    Log.i(TAG, "onResponse: $name")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                requestedByNames.remove(name)
                isRequesting.value = false
                Log.i(TAG, "onFailure: $name")
            }
        })
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
            pokemonList.value = (pokemonList.value + newPokemon).sortedBy { it.id }
        }
    }

    fun getPokemon(id: Int): Pokemon? {
        return pokemonList.value.find {
            it.id == id
        } ?: run {
            requestPokemon(id)
            null
        }
    }

    fun getPokemon(name: String): Pokemon? {
        return pokemonList.value.find {
            it.name == name
        } ?: run {
            requestPokemon(name)
            null
        }
    }
}