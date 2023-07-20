package com.project.mypokedex.network.services

import com.project.mypokedex.network.responses.EvolutionChainBase
import retrofit2.http.GET
import retrofit2.http.Path

interface EvolutionChainService {

    @GET("evolution-chain/{id}")
    suspend fun getEvolutionChain(@Path("id") id: Int): EvolutionChainBase
}