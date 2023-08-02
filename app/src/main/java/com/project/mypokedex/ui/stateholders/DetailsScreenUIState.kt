package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.EvolutionChain
import com.project.mypokedex.model.GroupSprite
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.SelectableSprite
import com.project.mypokedex.model.Sprite
import com.project.mypokedex.model.SpriteGender
import com.project.mypokedex.model.SpriteTypes
import com.project.mypokedex.model.SpriteVariant

data class DetailsScreenUIState(
    val pokemon: Pokemon? = null,
    val evolutionChain: EvolutionChain? = null,
    val varieties: List<List<Pokemon>> = emptyList(),
    val onPokemonClick: (String) -> Unit = {},
    val selectableSpriteOptions: List<SelectableSprite> = emptyList(),
    val spriteGroupOptions: List<GroupSprite> = emptyList(),
    val onSelectableSpriteOptionClick: (SelectableSprite) -> Unit = {},
    val onSpriteGroupOptionClick: (Sprite) -> Unit = {},
    val pokemonFrontImage: String = "",
    val pokemonBackImage: String = "",
    val hasReturnSprite: Boolean = false,
    val onReturnSpritesClick: () -> Unit = {},
    val spriteGenderOptions: SpriteGender? = null,
    val spriteVariantOptions: SpriteVariant? = null,
    val onSpriteTypeClick: (SpriteTypes) -> Unit = {}
)
