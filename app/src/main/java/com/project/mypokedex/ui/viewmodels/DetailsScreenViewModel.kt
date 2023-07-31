package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.project.mypokedex.extensions.toGrid
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.SelectableSprite
import com.project.mypokedex.model.Sprite
import com.project.mypokedex.model.SpriteGender
import com.project.mypokedex.model.SpriteType
import com.project.mypokedex.model.SpriteType.Companion.defaultType
import com.project.mypokedex.model.SpriteType.Companion.switchGender
import com.project.mypokedex.model.SpriteType.Companion.switchVariant
import com.project.mypokedex.model.SpriteTypes
import com.project.mypokedex.model.SpriteVariant
import com.project.mypokedex.model.Sprites
import com.project.mypokedex.navigation.MainNavComponent
import com.project.mypokedex.navigation.MainNavComponent.Companion.getSingleTopWithPopUpTo
import com.project.mypokedex.navigation.screens.DetailsScreen
import com.project.mypokedex.navigation.screens.DetailsScreen.pokemonIdArgument
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.DetailsScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PokemonRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailsScreenUIState> =
        MutableStateFlow(DetailsScreenUIState())
    val uiState get() = _uiState.asStateFlow()

    private val spritesStack = ArrayList<Sprite>()
    private var currentSpriteType = defaultType
    private var currentSelectableSprite: SelectableSprite? = null

    init {
        _uiState.update {
            it.copy(
                onPokemonClick = { pokemon ->
                    onPokemonClick(pokemon)
                },
                onSelectableSpriteOptionClick = { sprite ->
                    onSelectableSpriteOptionClick(sprite)
                },
                onSpriteGroupOptionClick = { sprite ->
                    onSpriteGroupOptionClick(sprite)
                },
                onReturnSpritesClick = {
                    onReturnSpritesClick()
                },
                onSpriteTypeClick = { spriteTypes ->
                    onSpriteTypeClick(spriteTypes)
                }
            )
        }

        CoroutineScope(IO).launch {
            savedStateHandle
                .getStateFlow<Int?>(pokemonIdArgument, null)
                .filterNotNull()
                .collect { id ->
                    repository.getPokemon(id)?.let {
                        setPokemon(it)
                        selectNewPokemonImage(
                            it,
                            it.getAvailableGifOrImageSprite(),
                            currentSpriteType
                        )
                        setEvolutionChain(it)
                        setVarieties(it)
                        onSpriteGroupOptionClick(it.sprites)
                    }
                }
        }
    }

    private fun onPokemonClick(pokemon: Pokemon) {
        MainNavComponent.navController.apply {
            val lastId =
                this.currentBackStackEntry?.arguments?.getInt(pokemonIdArgument)
            DetailsScreen.apply {
                val navOptions =
                    if (pokemon.id == lastId) getSingleTopWithPopUpTo(getRoute()) else navOptions {
                        popBackStack()
                    }
                navigateToItself(pokemonId = pokemon.id, navOptions = navOptions)
            }
        }
    }

    private fun setPokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    pokemon = pokemon
                )
            }
        }
    }

    private fun setEvolutionChain(pokemon: Pokemon) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    evolutionChain = repository.getEvolutionChainByPokemon(pokemon)
                )
            }
        }
    }

    private fun setVarieties(pokemon: Pokemon) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val varieties =
                    repository.getSpecies(pokemon).varieties.filter { it != pokemon }.toGrid(3)
                currentState.copy(
                    varieties = varieties
                )
            }
        }
    }

    private fun onReturnSpritesClick() {
        spritesStack.removeLast()
        updateSpriteOptionsList(spritesStack.last())
    }

    private fun onSelectableSpriteOptionClick(sprite: SelectableSprite) {
        currentSpriteType = defaultType
        selectNewPokemonImage(_uiState.value.pokemon, sprite, currentSpriteType)
    }

    private fun onSpriteGroupOptionClick(sprite: Sprite) {
        spritesStack.add(sprite)
        updateSpriteOptionsList(sprite)
    }

    private fun selectNewPokemonImage(
        pokemon: Pokemon?,
        sprite: SelectableSprite,
        spriteType: SpriteType
    ) {
        currentSelectableSprite = sprite
        viewModelScope.launch {
            val images = pokemon?.sprites?.getImagesFromSprite(sprite, spriteType) ?: Pair("", "")
            _uiState.update {
                it.copy(
                    pokemonFrontImage = images.first,
                    pokemonBackImage = images.second
                )
            }
        }

        setAvailableSpriteTypes(sprite)
    }

    private fun onSpriteTypeClick(spriteTypes: SpriteTypes) {
        when (spriteTypes) {
            SpriteTypes.Male,
            SpriteTypes.Female -> {
                currentSpriteType = currentSpriteType.switchGender()
            }

            SpriteTypes.Normal,
            SpriteTypes.Shiny -> {
                currentSpriteType = currentSpriteType.switchVariant()
            }

            else -> {}
        }

        currentSelectableSprite?.let {
            selectNewPokemonImage(
                _uiState.value.pokemon,
                it, currentSpriteType
            )
        }
    }

    private fun setAvailableSpriteTypes(sprite: SelectableSprite) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                spriteGenderOptions = getSpriteGenderOption(sprite),
                spriteVariantOptions = getSpriteVariantOption(sprite)
            )
        }
    }

    private fun getSpriteGenderOption(sprite: SelectableSprite): SpriteGender? {
        val genderOptions = sprite.getFrontSprites().map { it.gender }.toMutableList().distinct()
        return if (genderOptions.size > 1) {
            genderOptions.first { it == currentSpriteType.gender }
        } else {
            null
        }
    }

    private fun getSpriteVariantOption(sprite: SelectableSprite): SpriteVariant? {
        val variantOptions = sprite.getFrontSprites().map { it.variant }.toMutableList().distinct()
        return if (variantOptions.size > 1) {
            variantOptions.first { it == currentSpriteType.variant }
        } else {
            null
        }
    }

    private fun updateSpriteOptionsList(sprite: Sprite) {
        updateReturnSpritesButton(sprite)
        updateSelectableSpriteOptionsList(sprite)
        updateSpriteGroupOptionsList(sprite)
    }

    private fun updateReturnSpritesButton(sprite: Sprite) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    hasReturnSprite = sprite !is Sprites
                )
            }
        }
    }

    private fun updateSelectableSpriteOptionsList(sprite: Sprite) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    selectableSpriteOptions = sprite.getSelectableSpriteOptions()
                )
            }
        }
    }

    private fun updateSpriteGroupOptionsList(sprite: Sprite) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    spriteGroupOptions = sprite.getSpriteGroupOptions()
                )
            }
        }
    }
}