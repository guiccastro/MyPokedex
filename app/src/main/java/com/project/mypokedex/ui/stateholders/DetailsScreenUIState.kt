package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.EvolutionChain
import com.project.mypokedex.model.Pokemon

data class DetailsScreenUIState(
    val pokemon: Pokemon? = null,
    val evolutionChain: EvolutionChain? = null,
    val varieties: List<List<Pokemon>> = emptyList(),
    val onPokemonClick: (Pokemon) -> Unit = {},
)
