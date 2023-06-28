package com.project.mypokedex.ui.stateholders.gridscreen

import com.project.mypokedex.model.Pokemon

data class GridScreenUIState(
    val pokemonList: List<Pokemon> = emptyList(),
    val showList: Boolean = true,
    val isSearching: Boolean = true,
    val searchText: String = "",
    val onSearchChange: (String) -> Unit = {}
)
