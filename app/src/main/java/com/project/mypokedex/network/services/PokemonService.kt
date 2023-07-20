package com.project.mypokedex.network.services

import com.project.mypokedex.network.responses.BasicKeysResponse
import com.project.mypokedex.network.responses.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): PokemonResponse

    @GET("pokemon?limit=100000&offset=0")
    suspend fun getBasicKeys(): BasicKeysResponse

}