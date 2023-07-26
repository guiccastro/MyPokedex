package com.project.mypokedex.model

import com.squareup.moshi.Json
import kotlin.reflect.full.memberProperties
import kotlin.reflect.javaType

open class SpriteUtil {
    @OptIn(ExperimentalStdlibApi::class)
    fun getAvailableSprites(): List<String> {
        return this.javaClass.kotlin.memberProperties.filter { it.returnType.javaType == String::class.java }
            .map { it.name }
    }

    fun getAllPaths(): List<List<String>> {
        val allPaths = ArrayList<List<String>>()
        findPathsDFS(this, ArrayList(), allPaths)
        return allPaths
    }

    private fun findPathsDFS(
        currentItem: SpriteUtil,
        currentPath: ArrayList<String>,
        allPaths: ArrayList<List<String>>
    ) {
        currentPath.add((currentItem as SpriteOption).getName())

        if (currentItem.getSpritesOrigin().isEmpty()) {
            allPaths.add(currentPath.toList())
        } else {
            currentItem.getSpritesOrigin().forEach { item ->
                findPathsDFS(item, currentPath, allPaths)
            }
        }

        currentPath.removeAt(currentPath.size - 1)
    }

    fun getAllLeaves(): List<String> {
        val leaves = ArrayList<String>()
        findPathsDFS(this, leaves)
        return leaves
    }

    private fun findPathsDFS(
        currentItem: SpriteUtil,
        leaves: ArrayList<String>
    ) {
        if (currentItem.getSpritesOrigin().isEmpty()) {
            leaves.add(currentItem.javaClass.simpleName)
        } else {
            currentItem.getSpritesOrigin().forEach { item ->
                findPathsDFS(item, leaves)
            }
        }
    }

    fun getAllNodes(): List<SpriteUtil> {
        val allNodes = mutableListOf<SpriteUtil>()
        findNodesDFS(this, allNodes)
        return allNodes
    }

    fun findNodesDFS(node: SpriteUtil, allNodes: MutableList<SpriteUtil>) {
        allNodes.add(node)

        node.getSpritesOrigin(false).forEach {
            findNodesDFS(it, allNodes)
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun hasSpriteOptions(): Boolean {
        return this.javaClass.kotlin.memberProperties.any { it.returnType.javaType == String::class.java }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun hasOnlySpriteOptions(): Boolean {
        return this.javaClass.kotlin.memberProperties.all { it.returnType.javaType == String::class.java }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun getSpritesOrigin(inclusive: Boolean = true): List<SpriteUtil> {
        val list = ArrayList<SpriteUtil>()
        if (inclusive && hasSpriteOptions()) {
            list.add(this)
        }
        list.addAll(this.javaClass.kotlin.memberProperties.filter { it.returnType.javaType != String::class.java }
            .map { it.invoke(this) as SpriteUtil })
        return list
    }
}

interface SpriteOption {
    fun getName(): String
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
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Default"
}

data class SpriteOther(
    val dream_world: SpriteDreamWorld = SpriteDreamWorld(),
    val home: SpriteHome = SpriteHome(),
    @field:Json(name = "official-artwork") val official_artwork: SpriteOfficialArtwork = SpriteOfficialArtwork()
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Others"
}

data class SpriteDreamWorld(
    val front_default: String? = null,
    val front_female: String? = null
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Dream World"
}

data class SpriteHome(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null,
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Home"
}

data class SpriteOfficialArtwork(
    val front_default: String? = null,
    val front_shiny: String? = null
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Official Artwork"
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
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Versions"
}

data class SpriteGenerationI(
    @field:Json(name = "red-blue") val red_blue: SpriteRedBlue = SpriteRedBlue(),
    val yellow: SpriteYellow = SpriteYellow()
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Generation I"
}

data class SpriteGenerationII(
    val crystal: SpriteCrystal = SpriteCrystal(),
    val gold: SpriteGold = SpriteGold(),
    val silver: SpriteSilver = SpriteSilver()
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Generation II"
}

data class SpriteGenerationIII(
    val emerald: SpriteEmerald = SpriteEmerald(),
    @field:Json(name = "firered-leafgreen") val firered_leafgreen: SpriteFireRedLeafGreen = SpriteFireRedLeafGreen(),
    @field:Json(name = "ruby-sapphire") val ruby_sapphire: SpriteRubySapphire = SpriteRubySapphire()
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Generation III"
}

data class SpriteGenerationIV(
    @field:Json(name = "diamond-pearl") val diamond_pearl: SpriteDiamondPearl = SpriteDiamondPearl(),
    @field:Json(name = "heartgold-soulsilver") val heartgold_soulsilver: SpriteHeartGoldSoulSilver = SpriteHeartGoldSoulSilver(),
    val platinum: SpritePlatinum = SpritePlatinum()
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Generation IV"
}

data class SpriteGenerationV(
    @field:Json(name = "black-white") val black_white: SpriteBlackWhite = SpriteBlackWhite()
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Generation V"
}

data class SpriteGenerationVI(
    @field:Json(name = "omegaruby-alphasapphire") val omegaruby_alphasapphire: SpriteOmegaRubyAlphaSapphire = SpriteOmegaRubyAlphaSapphire(),
    @field:Json(name = "x-y") val x_y: SpriteXY = SpriteXY()
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Generation VI"
}

data class SpriteGenerationVII(
    val icons: SpriteIcons = SpriteIcons(),
    @field:Json(name = "ultra-sun-ultra-moon") val ultra_sun_ultra_moon: SpriteUltraSunUltraMoon = SpriteUltraSunUltraMoon()
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Generation VII"
}

data class SpriteGenerationVIII(
    val icons: SpriteIcons = SpriteIcons()
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Generation VIII"
}

data class SpriteRedBlue(
    val back_default: String? = null,
    val back_gray: String? = null,
    val back_transparent: String? = null,
    val front_default: String? = null,
    val front_gray: String? = null,
    val front_transparent: String? = null
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Red Blue"
}

data class SpriteYellow(
    val back_default: String? = null,
    val back_gray: String? = null,
    val back_transparent: String? = null,
    val front_default: String? = null,
    val front_gray: String? = null,
    val front_transparent: String? = null
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Yellow"
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
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Crystal"
}

data class SpriteGold(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null,
    val front_transparent: String? = null
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Gold"
}

data class SpriteSilver(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null,
    val front_transparent: String? = null
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Silver"
}

data class SpriteEmerald(
    val front_default: String? = null,
    val front_shiny: String? = null
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Emerald"
}

data class SpriteFireRedLeafGreen(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Fire Red Leaf Green"
}

data class SpriteRubySapphire(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Ruby Sapphire"
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
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Diamond Pearl"
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
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Hear Gold Soul Silver"
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
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Platinum"
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
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Black White"
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
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Animated"
}

data class SpriteOmegaRubyAlphaSapphire(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Omega Ruby Alpha Sapphire"
}

data class SpriteXY(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "XY"
}

data class SpriteIcons(
    val front_default: String? = null,
    val front_female: String? = null
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Icons"
}

data class SpriteUltraSunUltraMoon(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
) : SpriteUtil(), SpriteOption {
    override fun getName(): String = "Ultra Sun Ultra Moon"
}