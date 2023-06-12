package com.project.mypokedex.client

import io.github.resilience4j.retrofit.CircuitBreakerCallAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
            //.addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(getOkHttpClient())
            .build()
    }

    fun getClient(): PokemonClient {
        return getRetrofitInstance().create(PokemonClient::class.java)
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getLoggingInterceptor())
            .build()
    }
}