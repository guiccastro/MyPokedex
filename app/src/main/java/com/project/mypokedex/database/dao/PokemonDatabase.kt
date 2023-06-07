package com.project.mypokedex.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.mypokedex.database.converters.Converters
import com.project.mypokedex.model.Pokemon

@Database(version = 1, entities = [Pokemon::class])
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun PokemonDao(): PokemonDao

    companion object {
        private const val DATABASE_NAME = "pokemon.db"

        fun getDatabase(context: Context): PokemonDatabase {
            return Room.databaseBuilder(
                context,
                PokemonDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}