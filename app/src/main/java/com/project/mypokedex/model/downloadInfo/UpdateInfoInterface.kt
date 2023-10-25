package com.project.mypokedex.model.downloadInfo

import com.project.mypokedex.model.Pokemon

interface UpdateInfoInterface {

    val updateClass: UpdateClass

    fun needToRequest(pokemonList: List<Pokemon>): Boolean

    fun getRequestPokemons(pokemonList: List<Pokemon>): List<Int>
}