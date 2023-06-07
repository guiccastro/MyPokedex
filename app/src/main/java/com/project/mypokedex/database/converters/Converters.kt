package com.project.mypokedex.database.converters

import androidx.room.TypeConverter
import com.project.mypokedex.model.PokemonType

class Converters {

    @TypeConverter
    fun pokemonTypeToInt(types: List<PokemonType>): String {
        return types.joinToString("|")
    }

    @TypeConverter
    fun intToPokemonType(ids: String): List<PokemonType> {
        val list = ArrayList<PokemonType>()
        ids.split("|").forEach {
            PokemonType.fromId(it.toIntOrNull() ?: 0)?.let { type ->
                list.add(type)
            }
        }
        return list
    }
}