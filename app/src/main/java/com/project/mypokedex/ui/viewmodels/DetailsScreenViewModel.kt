package com.project.mypokedex.ui.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.project.mypokedex.extensions.toGrid
import com.project.mypokedex.model.Pokemon
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
import com.project.mypokedex.model.SpriteUtil
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
                onSpriteOriginClick = { sprite ->
                    setSpriteOrigin(sprite)
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
                        setSpriteOrigin(it.sprites)
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

    private fun setSpriteOrigin(spriteOrigin: SpriteUtil) {
        if (spriteOrigin == _uiState.value.currentSpriteOrigin || spriteOrigin.hasOnlySpriteOptions()) {
            _uiState.value.pokemon?.sprites?.let { sprites ->
                Log.println(Log.ASSERT, "url", getSprite(sprites, spriteOrigin))

                viewModelScope.launch {
                    _uiState.value = _uiState.value.copy(
                        pokemonImage = getSprite(sprites, spriteOrigin)
                    )
                }
            }
        } else {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(
                        currentSpriteOrigin = spriteOrigin
                    )
                }
            }
        }
    }

    private fun getSprite(currentSprite: Any, targetSprite: SpriteUtil): String {
        when (currentSprite) {
            is Sprites -> {
                return if (currentSprite == targetSprite) {
                    currentSprite.front_default ?: ""
                } else {
                    getSprite(currentSprite.other, targetSprite)
                        .ifBlank { getSprite(currentSprite.versions, targetSprite) }
                }
            }

            is SpriteOther -> {
                return getSprite(currentSprite.dream_world, targetSprite)
                    .ifBlank { getSprite(currentSprite.home, targetSprite) }
                    .ifBlank { getSprite(currentSprite.official_artwork, targetSprite) }
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
                return getSprite(currentSprite.generation_i, targetSprite)
                    .ifBlank { getSprite(currentSprite.generation_ii, targetSprite) }
                    .ifBlank { getSprite(currentSprite.generation_iii, targetSprite) }
                    .ifBlank { getSprite(currentSprite.generation_vi, targetSprite) }
                    .ifBlank { getSprite(currentSprite.generation_v, targetSprite) }
                    .ifBlank { getSprite(currentSprite.generation_vi, targetSprite) }
                    .ifBlank { getSprite(currentSprite.generation_vii, targetSprite) }
                    .ifBlank { getSprite(currentSprite.generation_viii, targetSprite) }
            }

            is SpriteGenerationI -> {
                return getSprite(currentSprite.red_blue, targetSprite)
                    .ifBlank { getSprite(currentSprite.yellow, targetSprite) }
            }

            is SpriteGenerationII -> {
                return getSprite(currentSprite.crystal, targetSprite)
                    .ifBlank { getSprite(currentSprite.gold, targetSprite) }
                    .ifBlank { getSprite(currentSprite.silver, targetSprite) }
            }

            is SpriteGenerationIII -> {
                return getSprite(currentSprite.emerald, targetSprite)
                    .ifBlank { getSprite(currentSprite.firered_leafgreen, targetSprite) }
                    .ifBlank { getSprite(currentSprite.ruby_sapphire, targetSprite) }
            }

            is SpriteGenerationIV -> {
                return getSprite(currentSprite.diamond_pearl, targetSprite)
                    .ifBlank { getSprite(currentSprite.heartgold_soulsilver, targetSprite) }
                    .ifBlank { getSprite(currentSprite.platinum, targetSprite) }
            }

            is SpriteGenerationV -> {
                return getSprite(currentSprite.black_white, targetSprite)
            }

            is SpriteGenerationVI -> {
                return getSprite(currentSprite.omegaruby_alphasapphire, targetSprite)
                    .ifBlank { getSprite(currentSprite.x_y, targetSprite) }
            }

            is SpriteGenerationVII -> {
                return getSprite(currentSprite.icons, targetSprite)
                    .ifBlank { getSprite(currentSprite.ultra_sun_ultra_moon, targetSprite) }
            }

            is SpriteGenerationVIII -> {
                return getSprite(currentSprite.icons, targetSprite)
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
                    getSprite(currentSprite.animated, targetSprite)
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