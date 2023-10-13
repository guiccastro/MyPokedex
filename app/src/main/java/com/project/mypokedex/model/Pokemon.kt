package com.project.mypokedex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Pokemon(
    @PrimaryKey val id: Int,
    val name: String,
    val types: List<PokemonType>,
    val speciesId: Int,
    val sprites: Sprites,
    val height: Int
) {
    var species: PokemonSpecies? = null

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
        return getGif().front_default.orEmpty()
            .ifBlank { getDefaultImage().front_default }.orEmpty()
    }

    private fun getGif(): SpriteAnimated {
        return sprites.versions.generation_v.black_white.animated
    }

    private fun getDefaultImage(): SpriteOfficialArtwork {
        return sprites.other.official_artwork
    }

    fun getAvailableGifOrImageSprite(): SelectableSprite {
        return if (getGif().hasSpriteByType(SpriteType.defaultType)) {
            getGif()
        } else {
            getDefaultImage()
        }
    }
}