package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.navigation.MainNavComponent.Companion.navController
import com.project.mypokedex.navigation.screens.DetailsScreen
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.GameScreenUIState
import com.project.mypokedex.ui.theme.CardColor
import com.project.mypokedex.ui.theme.CorrectAnswer
import com.project.mypokedex.ui.theme.WrongAnswer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<GameScreenUIState> =
        MutableStateFlow(GameScreenUIState())
    val uiState get() = _uiState.asStateFlow()

    private var verifypoints = true

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onOptionClick = { pokemon ->
                    onOptionClick(pokemon)
                },
                onClickNext = {
                    onClickNext()
                },
                onClickPokemon = {
                    onClickPokemon()
                }
            )
        }

        setGame()
    }

    private fun List<Pokemon>.getPokemon(): Pokemon? {
        if (isEmpty()) return null
        return this[indices.random()]
    }

    private fun setGame() {
        viewModelScope.launch {
            val options = repository.getRandomPokemons(3)
            _uiState.value = _uiState.value.copy(
                options = options,
                pokemon = options.getPokemon(),
                answered = false,
                isCorrect = false,
                buttonsUIState = emptyList()
            )
        }
        verifypoints = true
    }

    private fun onOptionClick(pokemon: Pokemon) {
        if (!_uiState.value.answered) {
            _uiState.value = _uiState.value.copy(
                answered = true,
                isCorrect = _uiState.value.pokemon?.id == pokemon.id
            )

            val buttonsUIState = _uiState.value.options.map {
                if (_uiState.value.answered) {
                    updatePoints()
                    if (_uiState.value.isCorrect) {
                        if (it == pokemon) {
                            Pair(CorrectAnswer, true)
                        } else {
                            Pair(CardColor, false)
                        }
                    } else {
                        if (it == pokemon) {
                            Pair(WrongAnswer, true)
                        } else {
                            if (it == _uiState.value.pokemon) {
                                Pair(CorrectAnswer, true)
                            } else {
                                Pair(CardColor, false)
                            }
                        }
                    }
                } else {
                    Pair(CardColor, true)
                }
            }

            _uiState.value = _uiState.value.copy(
                buttonsUIState = buttonsUIState
            )
        }
    }

    private fun onClickNext() {
        setGame()
    }

    private fun onClickPokemon() {
        navController.apply {
            DetailsScreen.apply {
                navigateToItself(pokemonId = _uiState.value.pokemon?.id)
            }
        }
    }

    private fun updatePoints() {
        if (verifypoints) {
            val isCorrect = _uiState.value.isCorrect
            var currentPoints = _uiState.value.currentPoints
            var highestPoints = _uiState.value.highestPoints

            if (isCorrect) {
                currentPoints++

                if (currentPoints > highestPoints) {
                    highestPoints = currentPoints
                }
            } else {
                currentPoints = 0
            }

            _uiState.update {
                it.copy(
                    highestPoints = highestPoints,
                    currentPoints = currentPoints
                )
            }
            verifypoints = false
        }
    }
}