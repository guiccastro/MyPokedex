package com.project.mypokedex.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Pokemon(
    @PrimaryKey val id: Int,
    val name: String,
    val types: List<PokemonType>,
    val speciesId: Int
) {
    @Ignore
    var species: PokemonSpecies? = null

    var sprites: Sprites? = null

    override fun toString(): String {
        return "$id|$name"
    }

    fun formattedID(): String {
        return "# ${id.toString().padStart(3, '0')}"
    }

    fun formattedName(): String {
        return name.uppercase()
    }

    fun getGifOrImage(): String {
        return getGif()?.front_default ?: sprites?.front_default ?: ""
    }

    fun getGif(): SpriteAnimated? {
        return sprites?.versions?.generation_v?.black_white?.animated
    }
}