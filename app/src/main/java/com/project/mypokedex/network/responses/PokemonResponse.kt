package com.project.mypokedex.network.responses

import com.squareup.moshi.Json

data class PokemonResponse(
    val id: Int,
    val name: String,
    val types: List<TypeListResponse>,
    val sprites: SpritesResponse
)

data class SpritesResponse(
    @field:Json(name = "front_default") val frontDefault: String?,
    val versions: SpriteVersionsResponse?
)

data class SpriteVersionsResponse(
    @field:Json(name = "generation-v") val generationV: GenerationVResponse?
)

data class GenerationVResponse(
    @field:Json(name = "black-white") val blackWhite: BlackWhiteResponse?
)

data class BlackWhiteResponse(
    val animated: AnimatedResponse?
)

data class AnimatedResponse(
    @field:Json(name = "front_default") val frontDefault: String?,
    @field:Json(name = "back_default") val backDefault: String?
)

data class TypeListResponse(
    val slot: Int,
    val type: TypeResponse
)

data class TypeResponse(
    val name: String,
    val url: String
)
