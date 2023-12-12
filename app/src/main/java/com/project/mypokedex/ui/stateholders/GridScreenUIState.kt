package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.Pokemon

data class GridScreenUIState(
    val pokemonList: List<Pokemon> = emptyList(),
    val isSearching: Boolean = true,
    val searchText: String = "",
    val onSearchChange: (String) -> Unit = {},
    val onPokemonClick: (Pokemon) -> Unit = {},
)
