package com.project.mypokedex.model.downloadInfo

import com.project.mypokedex.model.Pokemon

enum class UpdateInfo : UpdateInfoInterface {
    Height {
        override val updateClass: UpdateClass
            get() = UpdateClass.PokemonClass

        override fun needToRequest(pokemon: Pokemon): Boolean {
            return pokemon.height == -1
        }
    },

    Weight {
        override val updateClass: UpdateClass
            get() = UpdateClass.PokemonClass

        override fun needToRequest(pokemon: Pokemon): Boolean {
            return pokemon.weight == -1
        }

    };

    companion object {
        fun getProperties(updateClass: UpdateClass): List<UpdateInfo> {
            return UpdateInfo.values().filter { it.updateClass == updateClass }
        }
    }
}