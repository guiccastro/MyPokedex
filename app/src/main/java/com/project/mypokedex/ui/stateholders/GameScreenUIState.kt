package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.Pokemon

data class GameScreenUIState(
    val pokemon: Pokemon? = null,
    val options: List<Pokemon> = emptyList()
)
