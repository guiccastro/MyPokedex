package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.Pokemon

data class ListScreenUIState(
    val pokemonList: List<Pokemon> = emptyList(),
    val searchText: String = "",
    val onSearchChange: (String) -> Unit = {},
    val onPokemonClick: (Pokemon) -> Unit = {},
)
