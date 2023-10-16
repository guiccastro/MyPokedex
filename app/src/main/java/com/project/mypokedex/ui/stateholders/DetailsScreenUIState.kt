package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.SelectableSprite
import com.project.mypokedex.model.SpriteGender
import com.project.mypokedex.model.SpriteTypes
import com.project.mypokedex.model.SpriteVariant

data class DetailsScreenUIState(
    val pokemon: Pokemon? = null,
    val evolutionChain: List<List<Pokemon>> = emptyList(),
    val varieties: List<List<Pokemon>> = emptyList(),
    val onPokemonClick: (Pokemon) -> Unit = {},
    val spriteOptions: List<Pair<SelectableSprite, String>> = emptyList(),
    val onSpriteOptionClick: (SelectableSprite) -> Unit = {},
    val pokemonFrontImage: String = "",
    val pokemonBackImage: String = "",
    val spriteGenderOptions: SpriteGender? = null,
    val spriteVariantOptions: SpriteVariant? = null,
    val onSpriteTypeClick: (SpriteTypes) -> Unit = {},
    val personHeight: Int = 184,
    val onChangeHeightDialogState: (Boolean) -> Unit = {},
    val onSaveHeightDialog: (String) -> Unit = {},
    val verifyNewHeightText: (String) -> Unit = {},
    val heightDialogState: Boolean = false,
    val heightDialogStateError: Boolean = false
)
