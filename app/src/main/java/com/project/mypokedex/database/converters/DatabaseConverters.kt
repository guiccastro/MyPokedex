package com.project.mypokedex.database.converters

import androidx.room.TypeConverter
import com.project.mypokedex.model.PokemonType

class DatabaseConverters {

    @TypeConverter
    fun pokemonTypeToString(types: List<PokemonType>): String {
        return types.map { it.id }.joinToString("|")
    }

    @TypeConverter
    fun stringToPokemonType(ids: String): List<PokemonType> {
        val list = ArrayList<PokemonType>()
        ids.split("|").forEach {
            PokemonType.fromId(it.toIntOrNull() ?: 0)?.let { type ->
                list.add(type)
            }
        }
        return list
    }
}