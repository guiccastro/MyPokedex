package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.Pokemon

data class ListPokemonScreenUIState(
    val currentPokemonId: Int = 1,
    val pokemonList: List<Pokemon> = emptyList(),
    val searchText: String = "",
    val onSearchChange: (String) -> Unit = {},
    val onClickUp: () -> Unit = {},
    val onClickDown: () -> Unit = {},
    val onClickLeft: () -> Unit = {},
    val onClickRight: () -> Unit = {}
)