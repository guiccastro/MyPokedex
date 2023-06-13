package com.project.mypokedex.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.mypokedex.database.converters.DatabaseConverters
import com.project.mypokedex.database.dao.PokemonDao
import com.project.mypokedex.model.Pokemon

@Database(
    version = 1,
    entities = [Pokemon::class]
)
@TypeConverters(DatabaseConverters::class)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
}