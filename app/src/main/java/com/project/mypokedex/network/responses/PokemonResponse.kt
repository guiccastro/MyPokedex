package com.project.mypokedex.network.responses

import android.util.Log
import com.project.mypokedex.extensions.getIDFromURL
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.PokemonType
import com.project.mypokedex.model.Sprites

data class PokemonResponse(
    val id: Int,
    val name: String,
    val types: List<TypeListResponse>,
    val sprites: Sprites,
    val species: BasicResponse,
    val height: Int,
    val weight: Int
) {
    fun createPokemon(): Pokemon {
        Log.i("PokemonResponse", "createPokemon: Creating pokemon")
        val types = types.mapNotNull {
            PokemonType.fromId(
                it.type.url.getIDFromURL()
            )
        }
        val species = species.url.getIDFromURL()

        val newPokemon = Pokemon(id, name, types, species, sprites, height)
        Log.i("PokemonResponse", "createPokemon: Pokemon created $newPokemon")

        return newPokemon
    }
}

data class TypeListResponse(
    val slot: Int,
    val type: BasicResponse
)
