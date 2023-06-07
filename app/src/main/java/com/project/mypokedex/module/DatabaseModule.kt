package com.project.mypokedex.module

import android.content.Context
import com.project.mypokedex.database.dao.PokemonDao
import com.project.mypokedex.database.dao.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providePokemonDao(@ApplicationContext context: Context): PokemonDao {
        return PokemonDatabase.getDatabase(context).PokemonDao()
    }
}