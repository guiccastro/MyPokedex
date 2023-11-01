package com.project.mypokedex.model.downloadInfo

import com.project.mypokedex.model.Pokemon

enum class UpdateClass : UpdateClassInterface {
    PokemonClass {
        override fun needToRequest(pokemonList: List<Pokemon>): Boolean {
            return pokemonList.any { pokemon ->
                UpdateInfo.getProperties(this).any { it.needToRequest(pokemon) }
            }
        }
    },

    PokemonSpecies {
        override fun needToRequest(pokemonList: List<Pokemon>): Boolean {
            return pokemonList.any { pokemon ->
                UpdateInfo.getProperties(this).any { it.needToRequest(pokemon) }
            }
        }
    };

    companion object {
        fun getClasses(pokemonList: List<Pokemon>): List<UpdateClass> {
            return values().filter { it.needToRequest(pokemonList) }
        }
    }
}