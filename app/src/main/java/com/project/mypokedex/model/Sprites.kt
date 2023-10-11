package com.project.mypokedex.model

import androidx.annotation.StringRes
import com.project.mypokedex.R
import com.project.mypokedex.extensions.getSpriteType
import com.project.mypokedex.model.SpriteType.Companion.switchOrientation
import com.squareup.moshi.Json

interface Sprite {
    fun getName(): Int

    fun getAllSelectableSprites(): List<SelectableSprite> {
        val allSelectableSprites = ArrayList<SelectableSprite>()
        findSelectableSpritesDFS(this, allSelectableSprites)
        return allSelectableSprites
    }

    private fun findSelectableSpritesDFS(
        currentItem: Sprite,
        allSelectableSprites: ArrayList<SelectableSprite>
    ) {
        allSelectableSprites.addAll(currentItem.getSelectableSpriteOptions())

        if (currentItem.getSpriteGroupOptions().isNotEmpty()) {
            currentItem.getSpriteGroupOptions().forEach { item ->
                findSelectableSpritesDFS(item, allSelectableSprites)
            }
        }
    }

    fun getSelectableSpriteOptions(): List<SelectableSprite> {
        val list = ArrayList<SelectableSprite>()
        if (this is SelectableSprite) {
            list.add(this)
        }
        list.addAll(getAllOptions().filterIsInstance<SelectableSprite>().filter { it.hasSprites() })
        list.removeAll(getSpriteGroupOptions().filterIsInstance<SelectableSprite>().toSet())
        return list
    }

    fun getSpriteGroupOptions(): List<GroupSprite> {
        val list = ArrayList<GroupSprite>()
        list.addAll(getAllOptions().filterIsInstance<GroupSprite>().filter { it.hasOptions() })
        return list
    }

    fun hasOptions(): Boolean {
        return (getSelectableSpriteOptions().isNotEmpty() || getSpriteGroupOptions().isNotEmpty())
    }

    fun getAllOptions(): List<Any?>

    fun getImagesFromSprite(
        targetSprite: Sprite,
        targetSpriteType: SpriteType
    ): Pair<String, String> {
        when (this) {
            is Sprites -> {
                return if (this::class == targetSprite::class) {
                    getSpriteOrientationPairByType(targetSpriteType)
                } else {
                    other.getImagesFromSprite(targetSprite, targetSpriteType)
                        .ifFrontBlank {
                            versions.getImagesFromSprite(
                                targetSprite,
                                targetSpriteType
                            )
                        }
                }
            }

            is SpriteOther -> {
                return dream_world.getImagesFromSprite(targetSprite, targetSpriteType)
                    .ifFrontBlank {
                        home.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
                    .ifFrontBlank {
                        official_artwork.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
            }

            is SpriteDreamWorld -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteHome -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteOfficialArtwork -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteVersions -> {
                return generation_i.getImagesFromSprite(
                    targetSprite,
                    targetSpriteType
                )
                    .ifFrontBlank {
                        generation_ii.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
                    .ifFrontBlank {
                        generation_iii.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
                    .ifFrontBlank {
                        generation_iv.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
                    .ifFrontBlank {
                        generation_v.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
                    .ifFrontBlank {
                        generation_vi.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
                    .ifFrontBlank {
                        generation_vii.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
                    .ifFrontBlank {
                        generation_viii.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
            }

            is SpriteGenerationI -> {
                return red_blue.getImagesFromSprite(targetSprite, targetSpriteType)
                    .ifFrontBlank {
                        yellow.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
            }

            is SpriteGenerationII -> {
                return crystal.getImagesFromSprite(targetSprite, targetSpriteType)
                    .ifFrontBlank {
                        gold.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
                    .ifFrontBlank {
                        silver.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
            }

            is SpriteGenerationIII -> {
                return emerald.getImagesFromSprite(targetSprite, targetSpriteType)
                    .ifFrontBlank {
                        firered_leafgreen.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
                    .ifFrontBlank {
                        ruby_sapphire.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
            }

            is SpriteGenerationIV -> {
                return diamond_pearl.getImagesFromSprite(
                    targetSprite,
                    targetSpriteType
                )
                    .ifFrontBlank {
                        heartgold_soulsilver.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
                    .ifFrontBlank {
                        platinum.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
            }

            is SpriteGenerationV -> {
                return black_white.getImagesFromSprite(targetSprite, targetSpriteType)
            }

            is SpriteGenerationVI -> {
                return omegaruby_alphasapphire.getImagesFromSprite(
                    targetSprite,
                    targetSpriteType
                )
                    .ifFrontBlank {
                        x_y.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
            }

            is SpriteGenerationVII -> {
                return icons.getImagesFromSprite(targetSprite, targetSpriteType)
                    .ifFrontBlank {
                        ultra_sun_ultra_moon.getImagesFromSprite(
                            targetSprite,
                            targetSpriteType
                        )
                    }
            }

            is SpriteGenerationVIII -> {
                return icons.getImagesFromSprite(targetSprite, targetSpriteType)
            }

            is SpriteRedBlue -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteYellow -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteCrystal -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteGold -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteSilver -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteEmerald -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteFireRedLeafGreen -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteRubySapphire -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteDiamondPearl -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteHeartGoldSoulSilver -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpritePlatinum -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteBlackWhite -> {
                return if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                } else {
                    animated.getImagesFromSprite(targetSprite, targetSpriteType)
                }
            }

            is SpriteAnimated -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteOmegaRubyAlphaSapphire -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteXY -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteIcons -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }

            is SpriteUltraSunUltraMoon -> {
                if (this::class == targetSprite::class) {
                    return getSpriteOrientationPairByType(targetSpriteType)
                }
            }
        }

        return Pair("", "")
    }

    fun Pair<String, String>.ifFrontBlank(defaultValue: () -> Pair<String, String>): Pair<String, String> {
        return if (first.isBlank()) defaultValue() else this
    }
}

interface SelectableSprite : Sprite {
    fun getAllSprites(): List<String> {
        return getAllOptions().filterIsInstance<String?>().filterNotNull()
    }

    fun hasSprites(): Boolean {
        return getAllSprites().isNotEmpty()
    }

    fun getSpriteByType(spriteType: SpriteType): String {
        val index = getAllSprites().map { it.getSpriteType() }.indexOfFirst { it == spriteType }
        return getAllSprites().getOrNull(index) ?: ""
    }

    fun getSpriteOrientationPairByType(spriteType: SpriteType): Pair<String, String> {
        var spriteFrontType: SpriteType = spriteType
        var spriteBackType: SpriteType = spriteType

        if (spriteType.orientation == SpriteTypes.Front) {
            spriteBackType = spriteType.switchOrientation()
        } else {
            spriteFrontType = spriteType.switchOrientation()
        }

        return Pair(getSpriteByType(spriteFrontType), getSpriteByType(spriteBackType))
    }

    fun hasSpriteByType(spriteType: SpriteType): Boolean {
        return getAllSprites().map { it.getSpriteType() }.any { it == spriteType }
    }

    fun getFrontSprites(): List<SpriteType> {
        return getAllSprites().map { it.getSpriteType() }
            .filter { it.orientation == SpriteTypes.Front }
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

    @StringRes
    override fun getName(): Int = R.string.sprites_default_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_other_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_dream_world_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_home_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_official_artwork_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_versions_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_generationI_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_generationII_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_generationIII_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_generationIV_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_generationV_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_generationVI_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_generationVII_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_generationVIII_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_red_blue_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_yellow_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_crystal_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_gold_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_silver_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_emerald_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_fire_red_leaf_green_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_ruby_sapphire_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_diamond_pearl_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_heart_gold_soul_silver_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_platinum_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_black_white_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_animated_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_omega_ruby_alpha_sapphire_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_x_y_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_icons_title

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

    @StringRes
    override fun getName(): Int = R.string.sprites_ultra_sun_ultra_moon_title

    override fun getAllOptions(): List<Any?> {
        return listOf(
            front_default,
            front_female,
            front_shiny,
            front_shiny_female
        )
    }
}