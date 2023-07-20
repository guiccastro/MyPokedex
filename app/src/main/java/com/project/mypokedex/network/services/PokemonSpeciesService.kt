package com.project.mypokedex.network.services

import com.project.mypokedex.network.responses.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonSpeciesService {

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: Int): PokemonSpeciesResponse
}