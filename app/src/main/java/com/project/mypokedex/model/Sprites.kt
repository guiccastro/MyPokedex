package com.project.mypokedex.model

import com.squareup.moshi.Json

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
)

data class SpriteOther(
    val dream_world: SpriteDreamWorld = SpriteDreamWorld(),
    val home: SpriteHome = SpriteHome(),
    @field:Json(name = "official-artwork") val official_artwork: SpriteOfficialArtwork = SpriteOfficialArtwork()
)

data class SpriteDreamWorld(
    val front_default: String? = null,
    val front_female: String? = null
)

data class SpriteHome(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null,
)

data class SpriteOfficialArtwork(
    val front_default: String? = null,
    val front_shiny: String? = null
)

data class SpriteVersions(
    @field:Json(name = "generation-i") val generation_i: SpriteGenerationI = SpriteGenerationI(),
    @field:Json(name = "generation-ii") val generation_ii: SpriteGenerationII = SpriteGenerationII(),
    @field:Json(name = "generation-iii") val generation_iii: SpriteGenerationIII = SpriteGenerationIII(),
    @field:Json(name = "generation-iv") val generation_iv: SpriteGenerationIV = SpriteGenerationIV(),
    @field:Json(name = "generation-v") val generation_v: SpriteGenerationV = SpriteGenerationV(),
    @field:Json(name = "generation-vi") val generation_vi: SpriteGenerationVI = SpriteGenerationVI(),
    @field:Json(name = "generation-vii") val generation_vii: SpriteGenerationVII = SpriteGenerationVII(),
    @field:Json(name = "generation-viii") val generation_viii: SpriteGenerationVIII = SpriteGenerationVIII(),
)

data class SpriteGenerationI(
    @field:Json(name = "red-blue") val red_blue: SpriteRedBlue = SpriteRedBlue(),
    val yellow: SpriteYellow = SpriteYellow()
)

data class SpriteGenerationII(
    val crystal: SpriteCrystal = SpriteCrystal(),
    val gold: SpriteGold = SpriteGold(),
    val silver: SpriteSilver = SpriteSilver()
)

data class SpriteGenerationIII(
    val emerald: SpriteEmerald = SpriteEmerald(),
    @field:Json(name = "firered-leafgreen") val firered_leafgreen: SpriteFireRedLeafGreen = SpriteFireRedLeafGreen(),
    @field:Json(name = "ruby-sapphire") val ruby_sapphire: SpriteRubySapphire = SpriteRubySapphire()
)

data class SpriteGenerationIV(
    @field:Json(name = "diamond-pearl") val diamond_pearl: SpriteDiamondPearl = SpriteDiamondPearl(),
    @field:Json(name = "heartgold-soulsilver") val heartgold_soulsilver: SpriteHeartGoldSoulSilver = SpriteHeartGoldSoulSilver(),
    val platinum: SpritePlatinum = SpritePlatinum()
)

data class SpriteGenerationV(
    @field:Json(name = "black-white") val black_white: SpriteBlackWhite = SpriteBlackWhite()
)

data class SpriteGenerationVI(
    @field:Json(name = "omegaruby-alphasapphire") val omegaruby_alphasapphire: SpriteOmegaRubyAlphaSapphire = SpriteOmegaRubyAlphaSapphire(),
    @field:Json(name = "x-y") val x_y: SpriteXY = SpriteXY()
)

data class SpriteGenerationVII(
    val icons: SpriteIcons = SpriteIcons(),
    @field:Json(name = "ultra-sun-ultra-moon") val ultra_sun_ultra_moon: SpriteUltraSunUltraMoon = SpriteUltraSunUltraMoon()
)

data class SpriteGenerationVIII(
    val icons: SpriteIcons = SpriteIcons()
)

data class SpriteRedBlue(
    val back_default: String? = null,
    val back_gray: String? = null,
    val back_transparent: String? = null,
    val front_default: String? = null,
    val front_gray: String? = null,
    val front_transparent: String? = null
)

data class SpriteYellow(
    val back_default: String? = null,
    val back_gray: String? = null,
    val back_transparent: String? = null,
    val front_default: String? = null,
    val front_gray: String? = null,
    val front_transparent: String? = null
)

data class SpriteCrystal(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val back_shiny_transparent: String? = null,
    val back_transparent: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null,
    val front_shiny_transparent: String? = null,
    val front_transparent: String? = null
)

data class SpriteGold(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null,
    val front_transparent: String? = null
)

data class SpriteSilver(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null,
    val front_transparent: String? = null
)

data class SpriteEmerald(
    val front_default: String? = null,
    val front_shiny: String? = null
)

data class SpriteFireRedLeafGreen(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null
)

data class SpriteRubySapphire(
    val back_default: String? = null,
    val back_shiny: String? = null,
    val front_default: String? = null,
    val front_shiny: String? = null
)

data class SpriteDiamondPearl(
    val back_default: String? = null,
    val back_female: String? = null,
    val back_shiny: String? = null,
    val back_shiny_female: String? = null,
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
)

data class SpriteHeartGoldSoulSilver(
    val back_default: String? = null,
    val back_female: String? = null,
    val back_shiny: String? = null,
    val back_shiny_female: String? = null,
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
)

data class SpritePlatinum(
    val back_default: String? = null,
    val back_female: String? = null,
    val back_shiny: String? = null,
    val back_shiny_female: String? = null,
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
)

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
)

data class SpriteAnimated(
    val back_default: String? = null,
    val back_female: String? = null,
    val back_shiny: String? = null,
    val back_shiny_female: String? = null,
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
)

data class SpriteOmegaRubyAlphaSapphire(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
)

data class SpriteXY(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
)

data class SpriteIcons(
    val front_default: String? = null,
    val front_female: String? = null
)

data class SpriteUltraSunUltraMoon(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null
)