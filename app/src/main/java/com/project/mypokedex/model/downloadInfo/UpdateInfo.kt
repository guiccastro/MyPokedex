package com.project.mypokedex.model.downloadInfo

import androidx.annotation.StringRes
import com.project.mypokedex.R
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.PokemonColor

enum class UpdateInfo : UpdateInfoInterface {
    Height {
        override val updateClass: UpdateClass
            get() = UpdateClass.PokemonClass

        override fun needToRequest(pokemon: Pokemon): Boolean {
            return pokemon.height == -1
        }

        @StringRes
        override fun getDescription(): Int {
            return R.string.updateInfo_desc_height
        }
    },

    Weight {
        override val updateClass: UpdateClass
            get() = UpdateClass.PokemonClass

        override fun needToRequest(pokemon: Pokemon): Boolean {
            return pokemon.weight == -1
        }

        @StringRes
        override fun getDescription(): Int {
            return R.string.updateInfo_desc_weight
        }

    },

    Color {
        override val updateClass: UpdateClass
            get() = UpdateClass.PokemonSpecies

        override fun needToRequest(pokemon: Pokemon): Boolean {
            return pokemon.species?.color == PokemonColor.None
        }

        override fun getDescription(): Int {
            return R.string.updateInfo_desc_color
        }
    };

    companion object {
        fun getProperties(updateClass: UpdateClass): List<UpdateInfo> {
            return UpdateInfo.values().filter { it.updateClass == updateClass }
        }

        @StringRes
        fun getDescription(pokemonList: List<Pokemon>): List<Int> {
            return UpdateInfo.values()
                .filter { property -> pokemonList.any { property.needToRequest(it) } }
                .map { it.getDescription() }
        }
    }
}