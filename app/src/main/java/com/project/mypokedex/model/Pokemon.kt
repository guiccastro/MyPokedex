package com.project.mypokedex.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Pokemon(
    @PrimaryKey val id: Int,
    val name: String,
    val types: List<PokemonType>,
    val image: String,
    val gif: String,
    val backGif: String,
    val speciesId: Int
) {
    @Ignore
    var species: PokemonSpecies? = null

    override fun toString(): String {
        return "$id|$name|$types|$image|$gif"
    }

    fun formattedID(): String {
        return "# ${id.toString().padStart(3, '0')}"
    }

    fun formattedName(): String {
        return name.uppercase()
    }

    fun getGifOrImage(): String {
        return gif.ifBlank {
            image
        }
    }
}