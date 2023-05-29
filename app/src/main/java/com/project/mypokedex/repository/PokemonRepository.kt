package com.project.mypokedex.repository

import android.util.Log
import com.project.mypokedex.client.CircuitBreakerConfiguration
import com.project.mypokedex.client.PokemonClient
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.PokemonType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PokemonRepository {

    private val client = PokemonClient(CircuitBreakerConfiguration()).getClient()

    val pokemonList: MutableStateFlow<List<Pokemon>> = MutableStateFlow(emptyList())

    private fun requestPokemon(id: Int) {
        Log.println(Log.ASSERT, "RequestPokemon", id.toString())
        client.getPokemon(id).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let { pokemon ->
                    val jsonObj = Json.parseToJsonElement(pokemon).jsonObject
                    parseAndSave(jsonObj)
                }
            }
        })
    }

    private fun requestPokemon(name: String) {
        Log.println(Log.ASSERT, "RequestPokemon", name)
        client.getPokemon(name).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let { pokemon ->
                    val jsonObj = Json.parseToJsonElement(pokemon).jsonObject
                    parseAndSave(jsonObj)
                }
            }
        })
    }

    private fun parseAndSave(info: JsonObject) {
        val id = info["id"]?.jsonPrimitive?.content?.toInt()
        val name = info["name"]?.jsonPrimitive?.content
        val types = info["types"]?.jsonArray?.mapNotNull {
            it.jsonObject["type"]?.jsonObject?.get("name")?.jsonPrimitive?.content?.let { typeName ->
                PokemonType.fromName(typeName)
            }
        }
        val image = info["sprites"]?.jsonObject?.get("front_default")?.jsonPrimitive?.content
        val gif =
            info["sprites"]?.jsonObject?.get("versions")?.jsonObject?.get("generation-v")?.jsonObject?.get(
                "black-white"
            )?.jsonObject?.get("animated")?.jsonObject?.get("front_default")?.jsonPrimitive?.content

        if (id != null &&
            name != null &&
            types != null &&
            image != null &&
            gif != null
        ) {
            val newPokemon = Pokemon(id, name, types, image, gif)
            Log.println(Log.ASSERT, "NewPokemon", newPokemon.toString())
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