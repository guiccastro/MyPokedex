package com.project.mypokedex.di.modules

import com.project.mypokedex.network.services.EvolutionChainService
import com.project.mypokedex.network.services.PokemonService
import com.project.mypokedex.network.services.PokemonSpeciesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


private const val POKE_API_URL = "https://pokeapi.co/api/v2/"

@Module
@InstallIn(SingletonComponent::class)
class RestApiModule {

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(POKE_API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonClient(): PokemonService {
        return provideRetrofit().create(PokemonService::class.java)
    }

    @Provides
    @Singleton
    fun provideEvolutionChainClient(): EvolutionChainService {
        return provideRetrofit().create(EvolutionChainService::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonSpeciesClient(): PokemonSpeciesService {
        return provideRetrofit().create(PokemonSpeciesService::class.java)
    }
}