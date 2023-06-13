package com.project.mypokedex.network.services

import com.project.mypokedex.network.responses.BasicKeysResponse
import com.project.mypokedex.network.responses.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("/api/v2/pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): PokemonResponse

    @GET("/api/v2/pokemon/{id}")
    fun getPokemon(@Path("id") name: String): Call<String>

    @GET("/api/v2/pokemon?limit=100000&offset=0")
    suspend fun getBasicKeys(): BasicKeysResponse

}