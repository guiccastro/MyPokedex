package com.project.mypokedex.model

import com.squareup.moshi.Json

interface Sprite {
    fun getName(): String
    fun isSelectable(): Boolean
    fun getSelectableSpriteOptions(): List<Sprite>
    fun getSpriteGroupOptions(): List<Sprite>
}

data class Sprites(
    val back_default: String? = null,
    val back_female: String? = null,
    val back_shiny: String? = null,
    val back_shiny_female: String? = null,
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null,
    val other: SpriteOther = SpriteOther(),
    val versions: SpriteVersions = SpriteVersions()
) : Sprite {

    override fun getName(): String = "Default"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> {
        return listOf(
            other,
            versions
        )
    }
}

data class SpriteOther(
    val dream_world: SpriteDreamWorld = SpriteDreamWorld(),
    val home: SpriteHome = SpriteHome(),
    @field:Json(name = "official-artwork") val official_artwork: SpriteOfficialArtwork = SpriteOfficialArtwork()
) : Sprite {

    override fun getName(): String = "Others"

    override fun isSelectable(): Boolean = false

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            dream_world,
            home,
            official_artwork
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteDreamWorld(
    val front_default: String? = null,
    val front_female: String? = null
) : Sprite {

    override fun getName(): String = "Dream World"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteHome(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null,
) : Sprite {

    override fun getName(): String = "Home"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteOfficialArtwork(
    val front_default: String? = null,
    val front_shiny: String? = null
) : Sprite {

    override fun getName(): String = "Official Artwork"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteVersions(
    @field:Json(name = "generation-i") val generation_i: SpriteGenerationI = SpriteGenerationI(),
    @field:Json(name = "generation-ii") val generation_ii: SpriteGenerationII = SpriteGenerationII(),
    @field:Json(name = "generation-iii") val generation_iii: SpriteGenerationIII = SpriteGenerationIII(),
    @field:Json(name = "generation-iv") val generation_iv: SpriteGenerationIV = SpriteGenerationIV(),
    @field:Json(name = "generation-v") val generation_v: SpriteGenerationV = SpriteGenerationV(),
    @field:Json(name = "generation-vi") val generation_vi: SpriteGenerationVI = SpriteGenerationVI(),
    @field:Json(name = "generation-vii") val generation_vii: SpriteGenerationVII = SpriteGenerationVII(),
    @field:Json(name = "generation-viii") val generation_viii: SpriteGenerationVIII = SpriteGenerationVIII(),
) : Sprite {

    override fun getName(): String = "Versions"

    override fun isSelectable(): Boolean = false

    override fun getSelectableSpriteOptions(): List<Sprite> = emptyList()

    override fun getSpriteGroupOptions(): List<Sprite> {
        return listOf(
            generation_i,
            generation_ii,
            generation_iii,
            generation_iv,
            generation_v,
            generation_vi,
            generation_vii,
            generation_viii
        )
    }
}

data class SpriteGenerationI(
    @field:Json(name = "red-blue") val red_blue: SpriteRedBlue = SpriteRedBlue(),
    val yellow: SpriteYellow = SpriteYellow()
) : Sprite {

    override fun getName(): String = "Generation I"

    override fun isSelectable(): Boolean = false

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            red_blue,
            yellow
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteGenerationII(
    val crystal: SpriteCrystal = SpriteCrystal(),
    val gold: SpriteGold = SpriteGold(),
    val silver: SpriteSilver = SpriteSilver()
) : Sprite {

    override fun getName(): String = "Generation II"

    override fun isSelectable(): Boolean = false

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            crystal,
            gold,
            silver
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteGenerationIII(
    val emerald: SpriteEmerald = SpriteEmerald(),
    @field:Json(name = "firered-leafgreen") val firered_leafgreen: SpriteFireRedLeafGreen = SpriteFireRedLeafGreen(),
    @field:Json(name = "ruby-sapphire") val ruby_sapphire: SpriteRubySapphire = SpriteRubySapphire()
) : Sprite {

    override fun getName(): String = "Generation III"

    override fun isSelectable(): Boolean = false

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            emerald,
            firered_leafgreen,
            ruby_sapphire
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteGenerationIV(
    @field:Json(name = "diamond-pearl") val diamond_pearl: SpriteDiamondPearl = SpriteDiamondPearl(),
    @field:Json(name = "heartgold-soulsilver") val heartgold_soulsilver: SpriteHeartGoldSoulSilver = SpriteHeartGoldSoulSilver(),
    val platinum: SpritePlatinum = SpritePlatinum()
) : Sprite {

    override fun getName(): String = "Generation IV"

    override fun isSelectable(): Boolean = false

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            diamond_pearl,
            heartgold_soulsilver,
            platinum
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteGenerationV(
    @field:Json(name = "black-white") val black_white: SpriteBlackWhite = SpriteBlackWhite()
) : Sprite {

    override fun getName(): String = "Generation V"

    override fun isSelectable(): Boolean = false

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            black_white
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteGenerationVI(
    @field:Json(name = "omegaruby-alphasapphire") val omegaruby_alphasapphire: SpriteOmegaRubyAlphaSapphire = SpriteOmegaRubyAlphaSapphire(),
    @field:Json(name = "x-y") val x_y: SpriteXY = SpriteXY()
) : Sprite {

    override fun getName(): String = "Generation VI"

    override fun isSelectable(): Boolean = false

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            omegaruby_alphasapphire,
            x_y
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteGenerationVII(
    val icons: SpriteIcons = SpriteIcons(),
    @field:Json(name = "ultra-sun-ultra-moon") val ultra_sun_ultra_moon: SpriteUltraSunUltraMoon = SpriteUltraSunUltraMoon()
) : Sprite {

    override fun getName(): String = "Generation VII"

    override fun isSelectable(): Boolean = false

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            icons,
            ultra_sun_ultra_moon
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteGenerationVIII(
    val icons: SpriteIcons = SpriteIcons()
) : Sprite {

    override fun getName(): String = "Generation VIII"

    override fun isSelectable(): Boolean = false

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            icons
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteRedBlue(
    val back_default: String? = null,
    val back_gray: String? = null,
    val back_transparent: String? = null,
    val front_default: String? = null,
    val front_gray: String? = null,
    val front_transparent: String? = null
) : Sprite {

    override fun getName(): String = "Red Blue"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteYellow(
    val back_default: String? = null,
    val back_gray: String? = null,
    val back_transparent: String? = null,
    val front_default: String? = null,
    val front_gray: String? = null,
    val front_transparent: String? = null
) : Sprite {

    override fun getName(): String = "Yellow"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteCrystal(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val back_shiny_transparent: String? = null,
    val back_transparent: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null,
    val front_shiny_transparent: String? = null,
    val front_transparent: String? = null
) : Sprite {

    override fun getName(): String = "Crystal"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteGold(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null,
    val front_transparent: String? = null
) : Sprite {

    override fun getName(): String = "Gold"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteSilver(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null,
    val front_transparent: String? = null
) : Sprite {

    override fun getName(): String = "Silver"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteEmerald(
    val front_default: String? = null,
    val front_shiny: String? = null
) : Sprite {

    override fun getName(): String = "Emerald"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteFireRedLeafGreen(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null
) : Sprite {

    override fun getName(): String = "Fire Red Leaf Green"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteRubySapphire(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null
) : Sprite {

    override fun getName(): String = "Ruby Sapphire"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteDiamondPearl(
    val back_default: String? = null,
    val back_female: String? = null,
    val back_shiny: String? = null,
    val back_shiny_female: String? = null,
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : Sprite {

    override fun getName(): String = "Diamond Pearl"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteHeartGoldSoulSilver(
    val back_default: String? = null,
    val back_female: String? = null,
    val back_shiny: String? = null,
    val back_shiny_female: String? = null,
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : Sprite {

    override fun getName(): String = "Heart Gold Soul Silver"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpritePlatinum(
    val back_default: String? = null,
    val back_female: String? = null,
    val back_shiny: String? = null,
    val back_shiny_female: String? = null,
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : Sprite {

    override fun getName(): String = "Platinum"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteBlackWhite(
    val animated: SpriteAnimated = SpriteAnimated(),
    val back_default: String? = null,
    val back_female: String? = null,
    val back_shiny: String? = null,
    val back_shiny_female: String? = null,
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : Sprite {

    override fun getName(): String = "Black White"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this,
            animated
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteAnimated(
    val back_default: String? = null,
    val back_female: String? = null,
    val back_shiny: String? = null,
    val back_shiny_female: String? = null,
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : Sprite {

    override fun getName(): String = "Animated"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteOmegaRubyAlphaSapphire(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : Sprite {

    override fun getName(): String = "Omega Ruby Alpha Sapphire"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteXY(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : Sprite {

    override fun getName(): String = "XY"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteIcons(
    val front_default: String? = null,
    val front_female: String? = null
) : Sprite {

    override fun getName(): String = "Icons"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}

data class SpriteUltraSunUltraMoon(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : Sprite {

    override fun getName(): String = "Ultra Sun Ultra Moon"

    override fun isSelectable(): Boolean = true

    override fun getSelectableSpriteOptions(): List<Sprite> {
        return listOf(
            this
        )
    }

    override fun getSpriteGroupOptions(): List<Sprite> = emptyList()
}