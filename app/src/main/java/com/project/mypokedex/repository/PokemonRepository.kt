package com.project.mypokedex.repository

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import com.project.mypokedex.client.PokemonClient
import com.project.mypokedex.data.Pokemon
import com.project.mypokedex.data.PokemonType
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

    val pokemonList = MutableLiveData<SnapshotStateList<Pokemon>>(mutableStateListOf())

    private fun requestPokemon(id: Int) {
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
        val gif = info["sprites"]?.jsonObject?.get("versions")?.jsonObject?.get("generation-v")?.jsonObject?.get("black-white")?.jsonObject?.get("animated")?.jsonObject?.get("front_default")?.jsonPrimitive?.content

        if (id != null &&
            name != null &&
            types != null &&
            image != null &&
            gif != null) {
            pokemonList.value?.add(Pokemon(id, name, types, image, gif))
            pokemonList.value?.sortBy { it.id }
            pokemonList.postValue(pokemonList.value)
        }
    }

    fun getPokemon(id: Int): Pokemon? {
        return pokemonList.value?.find {
            it.id == id
        } ?: run {
            requestPokemon(id)
            null
        }
    }

    fun getPokemon(name: String): Pokemon? {
        return pokemonList.value?.find {
            it.name == name
        } ?: run {
            requestPokemon(name)
            null
        }
    }
}