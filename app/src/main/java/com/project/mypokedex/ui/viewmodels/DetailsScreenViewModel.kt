package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.project.mypokedex.extensions.toGrid
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.Sprite
import com.project.mypokedex.model.SpriteAnimated
import com.project.mypokedex.model.SpriteBlackWhite
import com.project.mypokedex.model.SpriteCrystal
import com.project.mypokedex.model.SpriteDiamondPearl
import com.project.mypokedex.model.SpriteDreamWorld
import com.project.mypokedex.model.SpriteEmerald
import com.project.mypokedex.model.SpriteFireRedLeafGreen
import com.project.mypokedex.model.SpriteGenerationI
import com.project.mypokedex.model.SpriteGenerationII
import com.project.mypokedex.model.SpriteGenerationIII
import com.project.mypokedex.model.SpriteGenerationIV
import com.project.mypokedex.model.SpriteGenerationV
import com.project.mypokedex.model.SpriteGenerationVI
import com.project.mypokedex.model.SpriteGenerationVII
import com.project.mypokedex.model.SpriteGenerationVIII
import com.project.mypokedex.model.SpriteGold
import com.project.mypokedex.model.SpriteHeartGoldSoulSilver
import com.project.mypokedex.model.SpriteHome
import com.project.mypokedex.model.SpriteIcons
import com.project.mypokedex.model.SpriteOfficialArtwork
import com.project.mypokedex.model.SpriteOmegaRubyAlphaSapphire
import com.project.mypokedex.model.SpriteOther
import com.project.mypokedex.model.SpritePlatinum
import com.project.mypokedex.model.SpriteRedBlue
import com.project.mypokedex.model.SpriteRubySapphire
import com.project.mypokedex.model.SpriteSilver
import com.project.mypokedex.model.SpriteUltraSunUltraMoon
import com.project.mypokedex.model.SpriteVersions
import com.project.mypokedex.model.SpriteXY
import com.project.mypokedex.model.SpriteYellow
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

    init {
        _uiState.update {
            it.copy(
                onPokemonClick = { pokemon ->
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
                },
                onSpriteOptionClick = { sprite ->
                    setSprites(sprite)
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
                        setEvolutionChain(it)
                        setSpecies(it)
                        setSprites(it.sprites)
                    }
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

    private fun setSpecies(pokemon: Pokemon) {
        viewModelScope.launch {
            _uiState.update {
                val varieties =
                    repository.getSpecies(pokemon).varieties.mapNotNull { pokemonVarieties ->
                        if (pokemonVarieties == pokemon) null else pokemonVarieties
                    }.toGrid(3)
                it.copy(
                    varieties = varieties
                )
            }
        }
    }

    private fun setSprites(sprite: Sprite) {
        if (_uiState.value.selectableSpriteOptions.contains(sprite)) {
            selectNewPokemonImage(sprite)
        } else {
            updateSpriteOptionsList(sprite)
        }
    }

    private fun selectNewPokemonImage(sprite: Sprite) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                pokemonImage = getImageFromSprite(_uiState.value.pokemon?.sprites, sprite)
            )
        }
    }

    private fun updateSpriteOptionsList(sprite: Sprite) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    selectableSpriteOptions = sprite.getSelectableSpriteOptions(),
                    spriteGroupOptions = sprite.getSpriteGroupOptions()
                )
            }
        }
    }

    private fun getImageFromSprite(currentSprite: Any?, targetSprite: Sprite): String {
        if (currentSprite == null) return ""
        when (currentSprite) {
            is Sprites -> {
                return if (currentSprite == targetSprite) {
                    currentSprite.front_default ?: ""
                } else {
                    getImageFromSprite(currentSprite.other, targetSprite)
                        .ifBlank { getImageFromSprite(currentSprite.versions, targetSprite) }
                }
            }

            is SpriteOther -> {
                return getImageFromSprite(currentSprite.dream_world, targetSprite)
                    .ifBlank { getImageFromSprite(currentSprite.home, targetSprite) }
                    .ifBlank { getImageFromSprite(currentSprite.official_artwork, targetSprite) }
            }

            is SpriteDreamWorld -> {
                return if (currentSprite == targetSprite) {
                    currentSprite.front_default ?: ""
                } else {
                    ""
                }
            }

            is SpriteHome -> {
                return if (currentSprite == targetSprite) {
                    currentSprite.front_default ?: ""
                } else {
                    ""
                }
            }

            is SpriteOfficialArtwork -> {
                return if (currentSprite == targetSprite) {
                    currentSprite.front_default ?: ""
                } else {
                    ""
                }
            }

            is SpriteVersions -> {
                return getImageFromSprite(currentSprite.generation_i, targetSprite)
                    .ifBlank { getImageFromSprite(currentSprite.generation_ii, targetSprite) }
                    .ifBlank { getImageFromSprite(currentSprite.generation_iii, targetSprite) }
                    .ifBlank { getImageFromSprite(currentSprite.generation_vi, targetSprite) }
                    .ifBlank { getImageFromSprite(currentSprite.generation_v, targetSprite) }
                    .ifBlank { getImageFromSprite(currentSprite.generation_vi, targetSprite) }
                    .ifBlank { getImageFromSprite(currentSprite.generation_vii, targetSprite) }
                    .ifBlank { getImageFromSprite(currentSprite.generation_viii, targetSprite) }
            }

            is SpriteGenerationI -> {
                return getImageFromSprite(currentSprite.red_blue, targetSprite)
                    .ifBlank { getImageFromSprite(currentSprite.yellow, targetSprite) }
            }

            is SpriteGenerationII -> {
                return getImageFromSprite(currentSprite.crystal, targetSprite)
                    .ifBlank { getImageFromSprite(currentSprite.gold, targetSprite) }
                    .ifBlank { getImageFromSprite(currentSprite.silver, targetSprite) }
            }

            is SpriteGenerationIII -> {
                return getImageFromSprite(currentSprite.emerald, targetSprite)
                    .ifBlank { getImageFromSprite(currentSprite.firered_leafgreen, targetSprite) }
                    .ifBlank { getImageFromSprite(currentSprite.ruby_sapphire, targetSprite) }
            }

            is SpriteGenerationIV -> {
                return getImageFromSprite(currentSprite.diamond_pearl, targetSprite)
                    .ifBlank {
                        getImageFromSprite(
                            currentSprite.heartgold_soulsilver,
                            targetSprite
                        )
                    }
                    .ifBlank { getImageFromSprite(currentSprite.platinum, targetSprite) }
            }

            is SpriteGenerationV -> {
                return getImageFromSprite(currentSprite.black_white, targetSprite)
            }

            is SpriteGenerationVI -> {
                return getImageFromSprite(currentSprite.omegaruby_alphasapphire, targetSprite)
                    .ifBlank { getImageFromSprite(currentSprite.x_y, targetSprite) }
            }

            is SpriteGenerationVII -> {
                return getImageFromSprite(currentSprite.icons, targetSprite)
                    .ifBlank {
                        getImageFromSprite(
                            currentSprite.ultra_sun_ultra_moon,
                            targetSprite
                        )
                    }
            }

            is SpriteGenerationVIII -> {
                return getImageFromSprite(currentSprite.icons, targetSprite)
            }

            is SpriteRedBlue -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }

            is SpriteYellow -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }

            is SpriteCrystal -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }

            is SpriteGold -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }

            is SpriteSilver -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }

            is SpriteEmerald -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }

            is SpriteFireRedLeafGreen -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }

            is SpriteRubySapphire -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }

            is SpriteDiamondPearl -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }

            is SpriteHeartGoldSoulSilver -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }

            is SpritePlatinum -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }

            is SpriteBlackWhite -> {
                return if (currentSprite == targetSprite) {
                    currentSprite.front_default ?: ""
                } else {
                    getImageFromSprite(currentSprite.animated, targetSprite)
                }
            }

            is SpriteAnimated -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }

            is SpriteOmegaRubyAlphaSapphire -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }

            is SpriteXY -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }

            is SpriteIcons -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }

            is SpriteUltraSunUltraMoon -> {
                if (currentSprite == targetSprite) {
                    return currentSprite.front_default ?: ""
                }
            }
        }

        return ""
    }
}