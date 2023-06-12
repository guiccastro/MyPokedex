package com.project.mypokedex.client

import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

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
    @field:Json(name = "front_default") val frontDefault: String?
)

data class TypeListResponse(
    val slot: Int,
    val type: TypeResponse
)

data class TypeResponse(
    val name: String,
    val url: String
)

data class BasicKeysResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<KeyResponse>
)

data class KeyResponse(
    val name: String,
    val url: String
)

interface PokemonClient {

    @GET("/api/v2/pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): PokemonResponse

    @GET("/api/v2/pokemon/{id}")
    fun getPokemon(@Path("id") name: String): Call<String>

    @GET("/api/v2/pokemon?limit=100000&offset=0")
    suspend fun getBasicKeys(): BasicKeysResponse

}