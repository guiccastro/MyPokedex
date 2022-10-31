package com.project.mypokedex.repository

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.project.mypokedex.client.PokemonClient
import com.project.mypokedex.data.PokemonBaseInfo
import com.project.mypokedex.data.PokemonType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PokemonRepository {

    private val client = PokemonClient.getClient()

    val pokemonList = mutableStateListOf<PokemonBaseInfo>()

    private suspend fun requestPokemon(id: Int) {
        withContext(Dispatchers.IO) {
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
    }

    private suspend fun requestPokemon(name: String) {
        withContext(Dispatchers.IO) {
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
    }

    private fun parseAndSave(info: JsonObject) {
        val id = info["id"]?.jsonPrimitive?.content?.toInt()
        val name = info["name"]?.jsonPrimitive?.content
        val types = info["types"]?.jsonArray?.mapNotNull {
            it.jsonObject["type"]?.jsonObject?.get("name")?.jsonPrimitive?.content?.let { typeName ->
                PokemonType(typeName)
            }
        }
        val image = info["sprites"]?.jsonObject?.get("front_default")?.jsonPrimitive?.content

        Log.println(Log.ASSERT, "Pokemon", "$id|$name|$types|$image")

        if (id != null && name != null && types != null && image != null) {
            pokemonList.add(PokemonBaseInfo(id, name, types, image))
            pokemonList.sortBy { it.id }
        }
    }

    suspend fun getPokemon(id: Int): PokemonBaseInfo? {
        return pokemonList.find {
            it.id == id
        } ?: run {
            requestPokemon(id)
            null
        }
    }

    suspend fun getPokemon(name: String): PokemonBaseInfo? {
        return pokemonList.find {
            it.name == name
        } ?: run {
            requestPokemon(name)
            null
        }
    }
}