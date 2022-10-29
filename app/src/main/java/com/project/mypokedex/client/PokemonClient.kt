package com.project.mypokedex.client

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object PokemonClient {

    private const val MAIN_URL = "https://pokeapi.co"

    private fun getRetrofitInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(MAIN_URL)
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    fun getClient(): Endpoint {
        return getRetrofitInstance().create(Endpoint::class.java)
    }
}