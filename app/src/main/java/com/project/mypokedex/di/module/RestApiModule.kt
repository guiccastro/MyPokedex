package com.project.mypokedex.di.module

import com.project.mypokedex.network.services.PokemonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.retrofit.CircuitBreakerCallAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Duration
import javax.inject.Singleton


private const val POKE_API_URL = "https://pokeapi.co"

@Module
@InstallIn(SingletonComponent::class)
class RestApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(circuitBreaker: CircuitBreaker, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(CircuitBreakerCallAdapter.of(circuitBreaker))
            .baseUrl(POKE_API_URL)
            //.addConverterFactory(GsonConverterFactory.create())
            //.addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(okHttpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(okHttpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Provides
    @Singleton
    fun provideCircuitBreaker(circuitBreakerConfig: CircuitBreakerConfig): CircuitBreaker {
        return CircuitBreakerRegistry.of(circuitBreakerConfig)
            .circuitBreaker("circuit-breaker-pokemon")
    }

    @Provides
    @Singleton
    fun provideCircuitBreakerConfig(): CircuitBreakerConfig {
        return CircuitBreakerConfig.custom()
            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            .slidingWindowSize(10)
            .slowCallRateThreshold(70.0f)
            .failureRateThreshold(70.0f)
            .waitDurationInOpenState(Duration.ofSeconds(5))
            .slowCallDurationThreshold(Duration.ofSeconds(5))
            .permittedNumberOfCallsInHalfOpenState(3)
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonClient(retrofit: Retrofit): PokemonService {
        return retrofit.create(PokemonService::class.java)
    }
}