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

    private val requestedByIDs: HashMap<Int, Boolean> = HashMap()
    private val requestedByNames: HashMap<String, Boolean> = HashMap()

    private val TAG = PokemonRepository::class.java.simpleName

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
        client.getPokemon(id).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                requestedByIDs.remove(id)
                Log.i(TAG, "onFailure: $id")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let { pokemon ->
                    val jsonObj = Json.parseToJsonElement(pokemon).jsonObject
                    parseAndSave(jsonObj)
                    requestedByIDs.remove(id)
                    Log.i(TAG, "onResponse: $id")
                }
            }
        })
    }

    private fun requestPokemon(name: String) {
        if (isAlreadyRequested(name)) return
        requestedByNames[name] = true

        Log.i(TAG, "requestPokemon: $name")
        client.getPokemon(name).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                requestedByNames.remove(name)
                Log.i(TAG, "onFailure: $name")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let { pokemon ->
                    val jsonObj = Json.parseToJsonElement(pokemon).jsonObject
                    parseAndSave(jsonObj)
                    requestedByNames.remove(name)
                    Log.i(TAG, "onResponse: $name")
                }
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