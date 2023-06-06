package com.project.mypokedex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Pokemon(
    @PrimaryKey val id: Int,
    val name: String,
    val types: List<PokemonType>,
    val image: String,
    val gif: String
) {
    override fun toString(): String {
        return "$id|$name|$types|$image|$gif"
    }

    fun typesToString(): String {
        var string = ""
        types.forEachIndexed { index, pokemonType ->
            string += pokemonType.toString()
            if (index != types.size - 1) {
                string += " "
            }
        }
        return string
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