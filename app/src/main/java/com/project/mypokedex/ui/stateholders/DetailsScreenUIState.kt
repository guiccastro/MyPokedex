package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.EvolutionChain
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.SpriteOption
import com.project.mypokedex.model.SpriteUtil

data class DetailsScreenUIState(
    val pokemon: Pokemon? = null,
    val evolutionChain: EvolutionChain? = null,
    val varieties: List<List<Pokemon>> = emptyList(),
    val onPokemonClick: (Pokemon) -> Unit = {},
    val spritesTypes: List<SpriteOption> = emptyList(),
    val currentSpriteOrigin: SpriteUtil? = null,
    val onSpriteOriginClick: (SpriteUtil) -> Unit = {},
    val pokemonImage: String = ""
)
