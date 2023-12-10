package com.project.mypokedex.ui.stateholders

import androidx.compose.ui.graphics.Color
import com.project.mypokedex.model.Pokemon

data class GameScreenUIState(
    val pokemon: Pokemon? = null,
    val options: List<Pokemon> = emptyList(),
    val onOptionClick: (Pokemon) -> Unit = {},
    val answered: Boolean = false,
    val isCorrect: Boolean = false,
    val buttonsUIState: List<Pair<Color, Boolean>> = emptyList(),
    val onClickNext: () -> Unit = {},
    val onClickPokemon: () -> Unit = {},
    val highestPoints: Int = 0,
    val currentPoints: Int = 0
)
