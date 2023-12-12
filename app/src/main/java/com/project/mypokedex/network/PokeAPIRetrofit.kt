package com.project.mypokedex.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object PokeAPIRetrofit {

    private const val POKE_API_URL = "https://pokeapi.co/api/v2/"

    fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(POKE_API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}