package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.EvolutionChain
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.Sprite

data class DetailsScreenUIState(
    val pokemon: Pokemon? = null,
    val evolutionChain: EvolutionChain? = null,
    val varieties: List<List<Pokemon>> = emptyList(),
    val onPokemonClick: (Pokemon) -> Unit = {},
    val selectableSpriteOptions: List<Sprite> = emptyList(),
    val spriteGroupOptions: List<Sprite> = emptyList(),
    val onSelectableSpriteOptionClick: (Sprite) -> Unit = {},
    val onSpriteGroupOptionClick: (Sprite) -> Unit = {},
    val pokemonImage: String = "",
    val hasReturnSprite: Boolean = false,
    val onReturnSpritesClick: () -> Unit = {}
)
