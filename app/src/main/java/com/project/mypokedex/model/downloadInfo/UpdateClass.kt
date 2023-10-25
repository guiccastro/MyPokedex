package com.project.mypokedex.model.downloadInfo

import com.project.mypokedex.model.Pokemon

enum class UpdateClass {
    PokemonClass,
    PokemonSpecies;

    companion object {
        fun getUpdateClass(pokemonList: List<Pokemon>): List<UpdateClass> {
            return UpdateInfo.values().filter { it.needToRequest(pokemonList) }.map { it.updateClass }.toSet().toList()
        }
    }
}