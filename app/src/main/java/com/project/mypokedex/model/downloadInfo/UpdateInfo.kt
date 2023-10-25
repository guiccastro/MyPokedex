package com.project.mypokedex.model.downloadInfo

import com.project.mypokedex.model.Pokemon

enum class UpdateInfo: UpdateInfoInterface {
    Height {
        override val updateClass: UpdateClass
            get() = UpdateClass.PokemonClass

        override fun needToRequest(pokemonList: List<Pokemon>): Boolean {
            return pokemonList.any { it.height == -1 }
        }

        override fun getRequestPokemons(pokemonList: List<Pokemon>): List<Int> {
            return pokemonList.filter { it.height == -1 }.map { it.id }
        }
    };

    companion object {
        fun needToRequestAnyPokemonInfo(pokemonList: List<Pokemon>): Boolean {
            return UpdateInfo.values().any { it.needToRequest(pokemonList) }
        }
    }
}