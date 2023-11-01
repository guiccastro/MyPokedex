package com.project.mypokedex.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.project.mypokedex.model.EvolutionChain
import com.project.mypokedex.model.PokemonColor
import com.project.mypokedex.model.PokemonGeneration
import com.project.mypokedex.model.PokemonSpecies
import com.project.mypokedex.model.PokemonType
import com.project.mypokedex.model.Sprites

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

    @TypeConverter
    fun spritesToString(sprites: Sprites): String {
        return Gson().toJson(sprites)
    }

    @TypeConverter
    fun stringToSprites(str: String): Sprites {
        return Gson().fromJson(str, Sprites::class.java)
    }

    @TypeConverter
    fun speciesToString(species: PokemonSpecies): String {
        return Gson().toJson(species)
    }

    @TypeConverter
    fun stringToSpecies(str: String): PokemonSpecies {
        return Gson().fromJson(str, PokemonSpecies::class.java)
    }

    @TypeConverter
    fun evolutionChainToString(evolutionChain: EvolutionChain): String {
        return Gson().toJson(evolutionChain)
    }

    @TypeConverter
    fun stringToEvolutionChain(str: String): EvolutionChain {
        return Gson().fromJson(str, EvolutionChain::class.java)
    }

    @TypeConverter
    fun varietiesToString(varieties: List<Int>): String {
        return varieties.joinToString("|")
    }

    @TypeConverter
    fun stringToVarieties(str: String): List<Int> {
        return str.split("|").map { it.toInt() }
    }

    @TypeConverter
    fun generationToString(generation: PokemonGeneration?): String {
        return Gson().toJson(generation)
    }

    @TypeConverter
    fun stringToGeneration(str: String): PokemonGeneration? {
        return Gson().fromJson(str, PokemonGeneration::class.java)
    }

    @TypeConverter
    fun pokemonColorToString(color: PokemonColor): String {
        return Gson().toJson(color)
    }

    @TypeConverter
    fun stringToPokemonColor(str: String): PokemonColor {
        return Gson().fromJson(str, PokemonColor::class.java)
    }
}