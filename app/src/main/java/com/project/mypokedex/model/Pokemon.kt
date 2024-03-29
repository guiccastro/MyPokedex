package com.project.mypokedex.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Pokemon(
    @PrimaryKey val id: Int,
    val name: String,
    val types: List<PokemonType>,
    val speciesId: Int,
    val sprites: Sprites,
    val height: Int,
    val weight: Int
) {

    @Embedded
    var species: PokemonSpecies? = null

    override fun toString(): String {
        return "$id|$name"
    }

    fun formattedID(): String {
        return "#${id.toString().padStart(3, '0')}"
    }

    fun formattedName(): String {
        return name.uppercase()
    }

    fun getGifOrImage(): String {
        return getGif().front_default.orEmpty()
            .ifBlank { getOfficialArtWork().front_default }.orEmpty()
            .ifBlank { getDefaultImage().front_default }.orEmpty()
    }

    private fun getGif(): SpriteAnimated {
        return sprites.versions.generation_v.black_white.animated
    }

    private fun getOfficialArtWork(): SpriteOfficialArtwork {
        return sprites.other.official_artwork
    }

    private fun getDefaultImage(): Sprites {
        return sprites
    }

    private fun getDreamWorldImage(): SpriteDreamWorld {
        return sprites.other.dream_world
    }

    fun getAvailableGifOrImageSprite(): SelectableSprite {
        return if (getGif().hasSpriteByType(SpriteType.defaultType)) {
            getGif()
        } else if (getOfficialArtWork().hasSpriteByType(SpriteType.defaultType)) {
            getOfficialArtWork()
        } else {
            getDefaultImage()
        }
    }

    fun getImageForHeightInfo(): String {
        return getDreamWorldImage().front_default.orEmpty()
            .ifBlank { getOfficialArtWork().front_default }.orEmpty()
            .ifBlank { getDefaultImage().front_default }.orEmpty()
    }
}