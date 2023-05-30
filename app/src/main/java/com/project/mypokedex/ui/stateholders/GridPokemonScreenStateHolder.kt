package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.Pokemon

data class GridPokemonScreenStateHolder(
    val pokemonList: List<Pokemon> = emptyList(),
    val onScroll: (Int) -> Unit = {}
)
