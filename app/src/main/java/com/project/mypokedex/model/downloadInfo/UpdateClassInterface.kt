package com.project.mypokedex.model.downloadInfo

import com.project.mypokedex.model.Pokemon

interface UpdateClassInterface {

    fun needToRequest(pokemonList: List<Pokemon>): Boolean
}