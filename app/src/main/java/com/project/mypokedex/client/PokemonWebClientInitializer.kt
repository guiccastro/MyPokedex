package com.project.mypokedex.client

import io.github.resilience4j.retrofit.CircuitBreakerCallAdapter
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject

class PokemonWebClientInitializer @Inject constructor(
    private val circuitBreakerConfiguration: CircuitBreakerConfiguration
) {
    companion object {
        private const val MAIN_URL = "https://pokeapi.co"
    }

    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(CircuitBreakerCallAdapter.of(circuitBreakerConfiguration.getCircuitBreaker()))
            .baseUrl(MAIN_URL)
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    fun getClient(): PokemonClient {
        return getRetrofitInstance().create(PokemonClient::class.java)
    }
}