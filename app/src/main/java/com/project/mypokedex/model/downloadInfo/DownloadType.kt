package com.project.mypokedex.model.downloadInfo

import com.project.mypokedex.model.Pokemon

enum class DownloadType : DownloadTypeInterface {
    None {
        override fun needToRequest(pokemonList: List<Pokemon>, totalPokemons: Int): Boolean {
            return true
        }

        override fun getRequestPokemons(
            pokemonBasicKeys: List<Int>,
            pokemonList: List<Pokemon>
        ): List<Int> {
            return emptyList()
        }
    },
    FullInfo {
        override fun needToRequest(pokemonList: List<Pokemon>, totalPokemons: Int): Boolean {
            return pokemonList.isEmpty() || pokemonList.size != totalPokemons
        }

        override fun getRequestPokemons(
            pokemonBasicKeys: List<Int>,
            pokemonList: List<Pokemon>
        ): List<Int> {
            return pokemonBasicKeys.filter { keyID -> pokemonList.indexOfFirst { it.id == keyID } == -1 }
        }
    },
    NewInfo {
        override fun needToRequest(pokemonList: List<Pokemon>, totalPokemons: Int): Boolean {
            return pokemonList.any { pokemon ->
                UpdateInfo.values().any { it.needToRequest(pokemon) }
            }
        }

        override fun getRequestPokemons(
            pokemonBasicKeys: List<Int>,
            pokemonList: List<Pokemon>
        ): List<Int> {
            return pokemonList.filter { pokemon ->
                UpdateInfo.values().any { it.needToRequest(pokemon) }
            }.map { it.id }
        }
    };

    companion object {
        fun getDownloadType(pokemonList: List<Pokemon>, totalPokemons: Int): DownloadType {
            return if (FullInfo.needToRequest(pokemonList, totalPokemons)) {
                FullInfo
            } else if (NewInfo.needToRequest(pokemonList, totalPokemons)) {
                NewInfo
            } else {
                None
            }
        }
    }
}

interface DownloadTypeInterface {
    fun needToRequest(pokemonList: List<Pokemon>, totalPokemons: Int): Boolean

    fun getRequestPokemons(pokemonBasicKeys: List<Int>, pokemonList: List<Pokemon>): List<Int>
}