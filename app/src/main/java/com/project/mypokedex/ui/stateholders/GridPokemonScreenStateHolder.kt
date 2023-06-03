package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.Pokemon

data class GridPokemonScreenStateHolder(
    val isDownloading: Boolean = true,
    val downloadProgress: Float = 0F,
    val formattedDownloadProgress: String = "",
    val pokemonList: List<Pokemon> = emptyList(),
    val showList: Boolean = true,
    val onSearchClick: () -> Unit = {},
    val isSearching: Boolean = true,
    val searchText: String = "",
    val onSearchChange: (String) -> Unit = {}
)
