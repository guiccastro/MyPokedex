package com.project.mypokedex.model.downloadInfo

import com.project.mypokedex.model.Pokemon

sealed class DownloadInfoType(val updateClassList: List<UpdateClass>?): DownloadInfoTypeInterface {
    class None: DownloadInfoType(null) {
        override fun getRequestPokemons(
            pokemonBasicKeys: List<Int>,
            pokemonList: List<Pokemon>
        ): List<Int> {
            return emptyList()
        }
    }

    class FullInfo: DownloadInfoType(null) {
        override fun getRequestPokemons(
            pokemonBasicKeys: List<Int>,
            pokemonList: List<Pokemon>
        ): List<Int> {
            return pokemonBasicKeys.filter { keyID -> pokemonList.indexOfFirst { it.id == keyID } == -1 }
        }
    }

    class NewInfo(updateClassList: List<UpdateClass>): DownloadInfoType(updateClassList) {
        override fun getRequestPokemons(
            pokemonBasicKeys: List<Int>,
            pokemonList: List<Pokemon>
        ): List<Int> {
            return pokemonList.filter { it.height == -1 }.map { it.id }
        }
    }

    override fun equals(other: Any?): Boolean {
        return other?.javaClass == this.javaClass
    }

    companion object {
        fun getPokemonDownloadInfo(pokemonList: List<Pokemon>, totalPokemons: Int): DownloadInfoType {
            return if (pokemonList.isEmpty() || pokemonList.size != totalPokemons) {
                FullInfo()
            } else if (UpdateInfo.needToRequestAnyPokemonInfo(pokemonList)) {
                NewInfo(UpdateClass.getUpdateClass(pokemonList))
            } else {
                None()
            }
        }
    }
}

interface DownloadInfoTypeInterface {
    fun getRequestPokemons(pokemonBasicKeys: List<Int>, pokemonList: List<Pokemon>): List<Int>
}