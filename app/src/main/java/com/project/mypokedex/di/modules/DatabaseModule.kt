package com.project.mypokedex.di.modules

import android.content.Context
import androidx.room.Room
import com.project.mypokedex.database.PokemonDatabase
import com.project.mypokedex.database.dao.PokemonDao
import com.project.mypokedex.network.services.EvolutionChainService
import com.project.mypokedex.network.services.PokemonService
import com.project.mypokedex.network.services.PokemonSpeciesService
import com.project.mypokedex.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "pokemon.db"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(database: PokemonDatabase): PokemonDao {
        return database.pokemonDao()
    }

    @Provides
    @Singleton
    fun provideRepository(
        dao: PokemonDao,
        pokemonClient: PokemonService,
        evolutionChainClient: EvolutionChainService,
        pokemonSpeciesClient: PokemonSpeciesService,
        @ApplicationContext context: Context
    ): PokemonRepository {
        return PokemonRepository(
            dao,
            pokemonClient,
            evolutionChainClient,
            pokemonSpeciesClient,
            context
        )
    }
}