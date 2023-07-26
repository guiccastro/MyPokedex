package com.project.mypokedex.model

import com.squareup.moshi.Json

interface Sprite {
    fun getName(): String

    fun getSelectableSpriteOptions(): List<Sprite> {
        val list = ArrayList<Sprite>()
        if (this is SelectableSprite) {
            list.add(this)
        }
        list.addAll(getAllOptions().filterIsInstance<SelectableSprite>().filter { it.hasSprites() })
        list.removeAll(getSpriteGroupOptions().toSet())
        return list
    }

    fun getSpriteGroupOptions(): List<Sprite> {
        val list = ArrayList<Sprite>()
        list.addAll(getAllOptions().filterIsInstance<GroupSprite>().filter { it.hasOptions() })
        return list
    }

    fun hasOptions(): Boolean {
        return (getSelectableSpriteOptions().isNotEmpty() || getSpriteGroupOptions().isNotEmpty())
    }

    fun getAllOptions(): List<Any?>
}

interface SelectableSprite : Sprite {
    fun getAllSprites(): List<String> {
        return getAllOptions().filterIsInstance<String?>().filterNotNull()
    }

    fun hasSprites(): Boolean {
        return getAllSprites().isNotEmpty()
    }
}

interface GroupSprite : Sprite

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
) : Sprite, SelectableSprite, GroupSprite {

    override fun getName(): String = "Default"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            back_default,
            back_female,
            back_shiny,
            back_shiny_female,
            front_default,
            front_female,
            front_shiny,
            front_shiny_female,
            other,
            versions
        )
    }
}

data class SpriteOther(
    val dream_world: SpriteDreamWorld = SpriteDreamWorld(),
    val home: SpriteHome = SpriteHome(),
    @field:Json(name = "official-artwork") val official_artwork: SpriteOfficialArtwork = SpriteOfficialArtwork()
) : Sprite, GroupSprite {

    override fun getName(): String = "Others"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            dream_world,
            home,
            official_artwork
        )
    }
}

data class SpriteDreamWorld(
    val front_default: String? = null,
    val front_female: String? = null
) : Sprite, SelectableSprite {

    override fun getName(): String = "Dream World"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            front_default,
            front_female
        )
    }
}

data class SpriteHome(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null,
) : Sprite, SelectableSprite {

    override fun getName(): String = "Home"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            front_default,
            front_female,
            front_shiny,
            front_shiny_female
        )
    }
}

data class SpriteOfficialArtwork(
    val front_default: String? = null,
    val front_shiny: String? = null
) : Sprite, SelectableSprite {

    override fun getName(): String = "Official Artwork"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            front_default,
            front_shiny
        )
    }
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
) : Sprite, GroupSprite {

    override fun getName(): String = "Versions"

    override fun getAllOptions(): List<Any?> {
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
) : Sprite, GroupSprite {

    override fun getName(): String = "Generation I"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            red_blue,
            yellow
        )
    }
}

data class SpriteGenerationII(
    val crystal: SpriteCrystal = SpriteCrystal(),
    val gold: SpriteGold = SpriteGold(),
    val silver: SpriteSilver = SpriteSilver()
) : Sprite, GroupSprite {

    override fun getName(): String = "Generation II"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            crystal,
            gold,
            silver
        )
    }
}

data class SpriteGenerationIII(
    val emerald: SpriteEmerald = SpriteEmerald(),
    @field:Json(name = "firered-leafgreen") val firered_leafgreen: SpriteFireRedLeafGreen = SpriteFireRedLeafGreen(),
    @field:Json(name = "ruby-sapphire") val ruby_sapphire: SpriteRubySapphire = SpriteRubySapphire()
) : Sprite, GroupSprite {

    override fun getName(): String = "Generation III"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            emerald,
            firered_leafgreen,
            ruby_sapphire
        )
    }
}

data class SpriteGenerationIV(
    @field:Json(name = "diamond-pearl") val diamond_pearl: SpriteDiamondPearl = SpriteDiamondPearl(),
    @field:Json(name = "heartgold-soulsilver") val heartgold_soulsilver: SpriteHeartGoldSoulSilver = SpriteHeartGoldSoulSilver(),
    val platinum: SpritePlatinum = SpritePlatinum()
) : Sprite, GroupSprite {

    override fun getName(): String = "Generation IV"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            diamond_pearl,
            heartgold_soulsilver,
            platinum
        )
    }
}

data class SpriteGenerationV(
    @field:Json(name = "black-white") val black_white: SpriteBlackWhite = SpriteBlackWhite()
) : Sprite, GroupSprite {

    override fun getName(): String = "Generation V"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            black_white
        )
    }
}

data class SpriteGenerationVI(
    @field:Json(name = "omegaruby-alphasapphire") val omegaruby_alphasapphire: SpriteOmegaRubyAlphaSapphire = SpriteOmegaRubyAlphaSapphire(),
    @field:Json(name = "x-y") val x_y: SpriteXY = SpriteXY()
) : Sprite, GroupSprite {

    override fun getName(): String = "Generation VI"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            omegaruby_alphasapphire,
            x_y
        )
    }
}

data class SpriteGenerationVII(
    val icons: SpriteIcons = SpriteIcons(),
    @field:Json(name = "ultra-sun-ultra-moon") val ultra_sun_ultra_moon: SpriteUltraSunUltraMoon = SpriteUltraSunUltraMoon()
) : Sprite, GroupSprite {

    override fun getName(): String = "Generation VII"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            icons,
            ultra_sun_ultra_moon
        )
    }
}

data class SpriteGenerationVIII(
    val icons: SpriteIcons = SpriteIcons()
) : Sprite, GroupSprite {

    override fun getName(): String = "Generation VIII"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            icons
        )
    }
}

data class SpriteRedBlue(
    val back_default: String? = null,
    val back_gray: String? = null,
    val back_transparent: String? = null,
    val front_default: String? = null,
    val front_gray: String? = null,
    val front_transparent: String? = null
) : Sprite, SelectableSprite {

    override fun getName(): String = "Red Blue"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            back_default,
            back_gray,
            back_transparent,
            front_default,
            front_gray,
            front_transparent
        )
    }
}

data class SpriteYellow(
    val back_default: String? = null,
    val back_gray: String? = null,
    val back_transparent: String? = null,
    val front_default: String? = null,
    val front_gray: String? = null,
    val front_transparent: String? = null
) : Sprite, SelectableSprite {

    override fun getName(): String = "Yellow"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            back_default,
            back_gray,
            back_transparent,
            front_default,
            front_gray,
            front_transparent
        )
    }
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
) : Sprite, SelectableSprite {

    override fun getName(): String = "Crystal"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            back_default,
            back_shiny,
            back_shiny_transparent,
            back_transparent,
            front_default,
            front_shiny,
            front_shiny_transparent,
            front_transparent
        )
    }
}

data class SpriteGold(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null,
    val front_transparent: String? = null
) : Sprite, SelectableSprite {

    override fun getName(): String = "Gold"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            back_default,
            back_shiny,
            front_default,
            front_shiny,
            front_transparent
        )
    }
}

data class SpriteSilver(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null,
    val front_transparent: String? = null
) : Sprite, SelectableSprite {

    override fun getName(): String = "Silver"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            back_default,
            back_shiny,
            front_default,
            front_shiny,
            front_transparent
        )
    }
}

data class SpriteEmerald(
    val front_default: String? = null,
    val front_shiny: String? = null
) : Sprite, SelectableSprite {

    override fun getName(): String = "Emerald"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            front_default,
            front_shiny
        )
    }
}

data class SpriteFireRedLeafGreen(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null
) : Sprite, SelectableSprite {

    override fun getName(): String = "Fire Red Leaf Green"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            back_default,
            back_shiny,
            front_default,
            front_shiny
        )
    }
}

data class SpriteRubySapphire(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null
) : Sprite, SelectableSprite {

    override fun getName(): String = "Ruby Sapphire"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            back_default,
            back_shiny,
            front_default,
            front_shiny
        )
    }
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
) : Sprite, SelectableSprite {

    override fun getName(): String = "Diamond Pearl"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            back_default,
            back_female,
            back_shiny,
            back_shiny_female,
            front_default,
            front_female,
            front_shiny,
            front_shiny_female
        )
    }
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
) : Sprite, SelectableSprite {

    override fun getName(): String = "Heart Gold Soul Silver"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            back_default,
            back_female,
            back_shiny,
            back_shiny_female,
            front_default,
            front_female,
            front_shiny,
            front_shiny_female
        )
    }
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
) : Sprite, SelectableSprite {

    override fun getName(): String = "Platinum"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            back_default,
            back_female,
            back_shiny,
            back_shiny_female,
            front_default,
            front_female,
            front_shiny,
            front_shiny_female
        )
    }
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
) : Sprite, SelectableSprite, GroupSprite {

    override fun getName(): String = "Black White"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            animated,
            back_default,
            back_female,
            back_shiny,
            back_shiny_female,
            front_default,
            front_female,
            front_shiny,
            front_shiny_female
        )
    }
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
) : Sprite, SelectableSprite {

    override fun getName(): String = "Animated"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            back_default,
            back_female,
            back_shiny,
            back_shiny_female,
            front_default,
            front_female,
            front_shiny,
            front_shiny_female
        )
    }
}

data class SpriteOmegaRubyAlphaSapphire(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : Sprite, SelectableSprite {

    override fun getName(): String = "Omega Ruby Alpha Sapphire"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            front_default,
            front_female,
            front_shiny,
            front_shiny_female
        )
    }
}

data class SpriteXY(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : Sprite, SelectableSprite {

    override fun getName(): String = "XY"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            front_default,
            front_female,
            front_shiny,
            front_shiny_female
        )
    }
}

data class SpriteIcons(
    val front_default: String? = null,
    val front_female: String? = null
) : Sprite, SelectableSprite {

    override fun getName(): String = "Icons"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            front_default,
            front_female
        )
    }
}

data class SpriteUltraSunUltraMoon(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : Sprite, SelectableSprite {

    override fun getName(): String = "Ultra Sun Ultra Moon"

    override fun getAllOptions(): List<Any?> {
        return listOf(
            front_default,
            front_female,
            front_shiny,
            front_shiny_female
        )
    }
}