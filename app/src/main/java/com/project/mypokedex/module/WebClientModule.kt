package com.project.mypokedex.module

import com.project.mypokedex.client.CircuitBreakerConfiguration
import com.project.mypokedex.client.PokemonClient
import com.project.mypokedex.client.PokemonWebClientInitializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WebClientModule {

    @Provides
    @Singleton
    fun providePokemonClient(circuitBreakerConfiguration: CircuitBreakerConfiguration): PokemonClient {
        return PokemonWebClientInitializer(circuitBreakerConfiguration).getClient()
    }
}