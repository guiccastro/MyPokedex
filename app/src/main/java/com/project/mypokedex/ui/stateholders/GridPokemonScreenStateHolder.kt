package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.Pokemon

data class GridPokemonScreenStateHolder(
    val pokemonList: List<Pokemon> = emptyList(),
    val showList: Boolean = true,
    val onScroll: (Int) -> Unit = {},
    val onSearchClick: () -> Unit = {},
    val isSearching: Boolean = true,
    val searchText: String = "",
    val onSearchChange: (String) -> Unit = {},
    val isRequesting: Boolean = false
)
