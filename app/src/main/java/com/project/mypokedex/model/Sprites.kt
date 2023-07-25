package com.project.mypokedex.model

import com.squareup.moshi.Json

data class Sprites(
    val back_default: String,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?,
    val other: SpriteOther,
    val versions: SpriteVersions
)

data class SpriteOther(
    val dream_world: SpriteDreamWorld,
    val home: SpriteHome,
    @field:Json(name = "official-artwork") val official_artwork: SpriteOfficialArtwork
)

data class SpriteDreamWorld(
    val front_default: String?,
    val front_female: String?
)

data class SpriteHome(
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?,
)

data class SpriteOfficialArtwork(
    val front_default: String?,
    val front_shiny: String?
)

data class SpriteVersions(
    @field:Json(name = "generation-i") val generation_i: SpriteGenerationI?,
    @field:Json(name = "generation-ii") val generation_ii: SpriteGenerationII?,
    @field:Json(name = "generation-iii") val generation_iii: SpriteGenerationIII?,
    @field:Json(name = "generation-iv") val generation_iv: SpriteGenerationIV?,
    @field:Json(name = "generation-v") val generation_v: SpriteGenerationV?,
    @field:Json(name = "generation-vi") val generation_vi: SpriteGenerationVI?,
    @field:Json(name = "generation-vii") val generation_vii: SpriteGenerationVII?,
    @field:Json(name = "generation-viii") val generation_viii: SpriteGenerationVIII?,
)

data class SpriteGenerationI(
    @field:Json(name = "red-blue") val red_blue: SpriteRedBlue,
    val yellow: SpriteYellow
)

data class SpriteGenerationII(
    val crystal: SpriteCrystal,
    val gold: SpriteGold,
    val silver: SpriteSilver
)

data class SpriteGenerationIII(
    val emerald: SpriteEmerald,
    @field:Json(name = "firered-leafgreen") val firered_leafgreen: SpriteFireRedLeafGreen,
    @field:Json(name = "ruby-sapphire") val ruby_sapphire: SpriteRubySapphire
)

data class SpriteGenerationIV(
    @field:Json(name = "diamond-pearl") val diamond_pearl: SpriteDiamondPearl,
    @field:Json(name = "heartgold-soulsilver") val heartgold_soulsilver: SpriteHeartGoldSoulSilver,
    val platinum: SpritePlatinum
)

data class SpriteGenerationV(
    @field:Json(name = "black-white") val black_white: SpriteBlackWhite?
)

data class SpriteGenerationVI(
    @field:Json(name = "omegaruby-alphasapphire") val omegaruby_alphasapphire: SpriteOmegaRubyAlphaSapphire?,
    @field:Json(name = "x-y") val x_y: SpriteXY?
)

data class SpriteGenerationVII(
    val icons: SpriteIcons?,
    @field:Json(name = "ultra-sun-ultra-moon") val ultra_sun_ultra_moon: SpriteUltraSunUltraMoon?
)

data class SpriteGenerationVIII(
    val icons: SpriteIcons?
)

data class SpriteRedBlue(
    val back_default: String?,
    val back_gray: String?,
    val back_transparent: String?,
    val front_default: String?,
    val front_gray: String?,
    val front_transparent: String?
)

data class SpriteYellow(
    val back_default: String?,
    val back_gray: String?,
    val back_transparent: String?,
    val front_default: String?,
    val front_gray: String?,
    val front_transparent: String?
)

data class SpriteCrystal(
    val back_default: String?,
    val back_shiny: String?,
    val back_shiny_transparent: String?,
    val back_transparent: String?,
    val front_default: String?,
    val front_shiny: String?,
    val front_shiny_transparent: String?,
    val front_transparent: String?
)

data class SpriteGold(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?,
    val front_transparent: String?
)

data class SpriteSilver(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?,
    val front_transparent: String?
)

data class SpriteEmerald(
    val front_default: String?,
    val front_shiny: String?
)

data class SpriteFireRedLeafGreen(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

data class SpriteRubySapphire(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

data class SpriteDiamondPearl(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

data class SpriteHeartGoldSoulSilver(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

data class SpritePlatinum(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

data class SpriteBlackWhite(
    val animated: SpriteAnimated?,
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

data class SpriteAnimated(
    val back_default: String?,
    val back_female: String?,
    val back_shiny: String?,
    val back_shiny_female: String?,
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

data class SpriteOmegaRubyAlphaSapphire(
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

data class SpriteXY(
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)

data class SpriteIcons(
    val front_default: String?,
    val front_female: String?
)

data class SpriteUltraSunUltraMoon(
    val front_default: String?,
    val front_female: String?,
    val front_shiny: String?,
    val front_shiny_female: String?
)