package com.project.mypokedex.model

import com.squareup.moshi.Json
import kotlin.reflect.full.memberProperties
import kotlin.reflect.javaType

open class Sprite {
    @OptIn(ExperimentalStdlibApi::class)
    private fun getSpriteGroups(): List<Sprite> {
        return this.javaClass.kotlin.memberProperties
            .filter { it.returnType.javaType != String::class.java }
            .map { it.invoke(this) as Sprite }
    }

    fun getSelectableSpriteOptions(): List<Sprite> {
        val list = ArrayList<Sprite>()
        if ((this as SpriteOption).isSelectable()) {
            list.add(this)
        }
        list.addAll(getSpriteGroups().filter { (it as SpriteOption).isSelectable() })
        return list
    }

    fun getSpriteGroupOptions(): List<Sprite> {
        val list = ArrayList<Sprite>()
        list.addAll(getSpriteGroups().filter { !(it as SpriteOption).isSelectable() })
        return list
    }
}

interface SpriteOption {
    fun getName(): String

    fun isSelectable(): Boolean
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
) : Sprite(), SpriteOption {
    override fun getName(): String = "Default"
    override fun isSelectable(): Boolean = true
}

data class SpriteOther(
    val dream_world: SpriteDreamWorld = SpriteDreamWorld(),
    val home: SpriteHome = SpriteHome(),
    @field:Json(name = "official-artwork") val official_artwork: SpriteOfficialArtwork = SpriteOfficialArtwork()
) : Sprite(), SpriteOption {
    override fun getName(): String = "Others"
    override fun isSelectable(): Boolean = false
}

data class SpriteDreamWorld(
    val front_default: String? = null,
    val front_female: String? = null
) : Sprite(), SpriteOption {
    override fun getName(): String = "Dream World"
    override fun isSelectable(): Boolean = true
}

data class SpriteHome(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null,
) : Sprite(), SpriteOption {
    override fun getName(): String = "Home"
    override fun isSelectable(): Boolean = true
}

data class SpriteOfficialArtwork(
    val front_default: String? = null,
    val front_shiny: String? = null
) : Sprite(), SpriteOption {
    override fun getName(): String = "Official Artwork"
    override fun isSelectable(): Boolean = true
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
) : Sprite(), SpriteOption {
    override fun getName(): String = "Versions"
    override fun isSelectable(): Boolean = false
}

data class SpriteGenerationI(
    @field:Json(name = "red-blue") val red_blue: SpriteRedBlue = SpriteRedBlue(),
    val yellow: SpriteYellow = SpriteYellow()
) : Sprite(), SpriteOption {
    override fun getName(): String = "Generation I"
    override fun isSelectable(): Boolean = false
}

data class SpriteGenerationII(
    val crystal: SpriteCrystal = SpriteCrystal(),
    val gold: SpriteGold = SpriteGold(),
    val silver: SpriteSilver = SpriteSilver()
) : Sprite(), SpriteOption {
    override fun getName(): String = "Generation II"
    override fun isSelectable(): Boolean = false
}

data class SpriteGenerationIII(
    val emerald: SpriteEmerald = SpriteEmerald(),
    @field:Json(name = "firered-leafgreen") val firered_leafgreen: SpriteFireRedLeafGreen = SpriteFireRedLeafGreen(),
    @field:Json(name = "ruby-sapphire") val ruby_sapphire: SpriteRubySapphire = SpriteRubySapphire()
) : Sprite(), SpriteOption {
    override fun getName(): String = "Generation III"
    override fun isSelectable(): Boolean = false
}

data class SpriteGenerationIV(
    @field:Json(name = "diamond-pearl") val diamond_pearl: SpriteDiamondPearl = SpriteDiamondPearl(),
    @field:Json(name = "heartgold-soulsilver") val heartgold_soulsilver: SpriteHeartGoldSoulSilver = SpriteHeartGoldSoulSilver(),
    val platinum: SpritePlatinum = SpritePlatinum()
) : Sprite(), SpriteOption {
    override fun getName(): String = "Generation IV"
    override fun isSelectable(): Boolean = false
}

data class SpriteGenerationV(
    @field:Json(name = "black-white") val black_white: SpriteBlackWhite = SpriteBlackWhite()
) : Sprite(), SpriteOption {
    override fun getName(): String = "Generation V"
    override fun isSelectable(): Boolean = false
}

data class SpriteGenerationVI(
    @field:Json(name = "omegaruby-alphasapphire") val omegaruby_alphasapphire: SpriteOmegaRubyAlphaSapphire = SpriteOmegaRubyAlphaSapphire(),
    @field:Json(name = "x-y") val x_y: SpriteXY = SpriteXY()
) : Sprite(), SpriteOption {
    override fun getName(): String = "Generation VI"
    override fun isSelectable(): Boolean = false
}

data class SpriteGenerationVII(
    val icons: SpriteIcons = SpriteIcons(),
    @field:Json(name = "ultra-sun-ultra-moon") val ultra_sun_ultra_moon: SpriteUltraSunUltraMoon = SpriteUltraSunUltraMoon()
) : Sprite(), SpriteOption {
    override fun getName(): String = "Generation VII"
    override fun isSelectable(): Boolean = false
}

data class SpriteGenerationVIII(
    val icons: SpriteIcons = SpriteIcons()
) : Sprite(), SpriteOption {
    override fun getName(): String = "Generation VIII"
    override fun isSelectable(): Boolean = false
}

data class SpriteRedBlue(
    val back_default: String? = null,
    val back_gray: String? = null,
    val back_transparent: String? = null,
    val front_default: String? = null,
    val front_gray: String? = null,
    val front_transparent: String? = null
) : Sprite(), SpriteOption {
    override fun getName(): String = "Red Blue"
    override fun isSelectable(): Boolean = true
}

data class SpriteYellow(
    val back_default: String? = null,
    val back_gray: String? = null,
    val back_transparent: String? = null,
    val front_default: String? = null,
    val front_gray: String? = null,
    val front_transparent: String? = null
) : Sprite(), SpriteOption {
    override fun getName(): String = "Yellow"
    override fun isSelectable(): Boolean = true
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
) : Sprite(), SpriteOption {
    override fun getName(): String = "Crystal"
    override fun isSelectable(): Boolean = true
}

data class SpriteGold(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null,
    val front_transparent: String? = null
) : Sprite(), SpriteOption {
    override fun getName(): String = "Gold"
    override fun isSelectable(): Boolean = true
}

data class SpriteSilver(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null,
    val front_transparent: String? = null
) : Sprite(), SpriteOption {
    override fun getName(): String = "Silver"
    override fun isSelectable(): Boolean = true
}

data class SpriteEmerald(
    val front_default: String? = null,
    val front_shiny: String? = null
) : Sprite(), SpriteOption {
    override fun getName(): String = "Emerald"
    override fun isSelectable(): Boolean = true
}

data class SpriteFireRedLeafGreen(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null
) : Sprite(), SpriteOption {
    override fun getName(): String = "Fire Red Leaf Green"
    override fun isSelectable(): Boolean = true
}

data class SpriteRubySapphire(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null
) : Sprite(), SpriteOption {
    override fun getName(): String = "Ruby Sapphire"
    override fun isSelectable(): Boolean = true
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
) : Sprite(), SpriteOption {
    override fun getName(): String = "Diamond Pearl"
    override fun isSelectable(): Boolean = true
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
) : Sprite(), SpriteOption {
    override fun getName(): String = "Hear Gold Soul Silver"
    override fun isSelectable(): Boolean = true
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
) : Sprite(), SpriteOption {
    override fun getName(): String = "Platinum"
    override fun isSelectable(): Boolean = true
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
) : Sprite(), SpriteOption {
    override fun getName(): String = "Black White"
    override fun isSelectable(): Boolean = true
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
) : Sprite(), SpriteOption {
    override fun getName(): String = "Animated"
    override fun isSelectable(): Boolean = true
}

data class SpriteOmegaRubyAlphaSapphire(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : Sprite(), SpriteOption {
    override fun getName(): String = "Omega Ruby Alpha Sapphire"
    override fun isSelectable(): Boolean = true
}

data class SpriteXY(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : Sprite(), SpriteOption {
    override fun getName(): String = "XY"
    override fun isSelectable(): Boolean = true
}

data class SpriteIcons(
    val front_default: String? = null,
    val front_female: String? = null
) : Sprite(), SpriteOption {
    override fun getName(): String = "Icons"
    override fun isSelectable(): Boolean = true
}

data class SpriteUltraSunUltraMoon(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : Sprite(), SpriteOption {
    override fun getName(): String = "Ultra Sun Ultra Moon"
    override fun isSelectable(): Boolean = true
}