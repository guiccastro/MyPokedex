package com.project.mypokedex.model.downloadInfo

import com.project.mypokedex.model.Pokemon

interface DownloadTypeInterface {
    fun needToRequest(pokemonList: List<Pokemon>, totalPokemons: Int): Boolean

    fun getRequestPokemons(pokemonBasicKeys: List<Int>, pokemonList: List<Pokemon>): List<Int>
}