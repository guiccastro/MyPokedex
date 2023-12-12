package com.project.mypokedex.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.MyPokedexApplication
import com.project.mypokedex.getGameAnswered
import com.project.mypokedex.getGameCurrentOptions
import com.project.mypokedex.getGameCurrentPoints
import com.project.mypokedex.getGameHighestPoints
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.navigation.MainNavComponent.Companion.navController
import com.project.mypokedex.navigation.screens.DetailsScreen
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.saveGameAnswered
import com.project.mypokedex.saveGameCurrentOptions
import com.project.mypokedex.saveGameCurrentPoints
import com.project.mypokedex.saveGameHighestPoints
import com.project.mypokedex.ui.stateholders.GameScreenUIState
import com.project.mypokedex.ui.theme.CardColor
import com.project.mypokedex.ui.theme.CorrectAnswer
import com.project.mypokedex.ui.theme.WrongAnswer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameScreenViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<GameScreenUIState> =
        MutableStateFlow(GameScreenUIState())
    val uiState get() = _uiState.asStateFlow()

    private var verifyPoints = true

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

        CoroutineScope(IO).launch {
            getGameHighestPoints(MyPokedexApplication.getInstance()).collect { highestPoints ->
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(
                            highestPoints = highestPoints
                        )
                    }
                }
            }
        }

        val context = MyPokedexApplication.getInstance()
        CoroutineScope(IO).launch {
            getGameCurrentPoints(context).collect { currentPoints ->
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(
                            currentPoints = currentPoints
                        )
                    }
                }
            }
        }

        CoroutineScope(IO).launch {
            val currentOptions = getGameCurrentOptions(context)
            Log.println(Log.ASSERT, "getGameCurrentOptions", currentOptions.toString())
            val listPairOptions =
                currentOptions?.map { Pair(it.first, PokemonRepository.getPokemon(it.second)) }
            val options = listPairOptions?.mapNotNull { it.second }
            val pokemon =
                listPairOptions?.filter { it.first }?.firstNotNullOfOrNull { it.second }
            val answered = getGameAnswered(context)
            if (!answered && options != null && options.size == 3 && pokemon != null) {
                viewModelScope.launch {
                    _uiState.update {
                        it.copy(
                            options = options,
                            pokemon = pokemon
                        )
                    }
                }
            } else {
                setGame()
            }
        }
    }

    private fun List<Pokemon>.getPokemon(): Pokemon? {
        if (isEmpty()) return null
        return this[indices.random()]
    }

    private fun setGame() {
        viewModelScope.launch {
            val options = PokemonRepository.getRandomPokemons(3)
            val pokemon = options.getPokemon()
            _uiState.value = _uiState.value.copy(
                options = options,
                pokemon = pokemon,
                answered = false,
                isCorrect = false,
                buttonsUIState = emptyList()
            )

            withContext(IO) {
                val context = MyPokedexApplication.getInstance()
                val pairOptions = options.map { Pair(it.id == pokemon?.id, it.id) }
                saveGameCurrentOptions(context, pairOptions)
                saveGameAnswered(context, false)
            }
        }
    }

    private fun onOptionClick(pokemon: Pokemon) {
        if (!_uiState.value.answered) {
            _uiState.value = _uiState.value.copy(
                answered = true,
                isCorrect = _uiState.value.pokemon?.id == pokemon.id
            )

            viewModelScope.launch {
                withContext(IO) {
                    saveGameAnswered(MyPokedexApplication.getInstance(), true)
                }
            }

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
        verifyPoints = true
    }

    private fun onClickPokemon() {
        navController.apply {
            DetailsScreen.apply {
                navigateToItself(pokemonId = _uiState.value.pokemon?.id)
            }
        }
    }

    private fun updatePoints() {
        if (verifyPoints) {
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
            verifyPoints = false

            viewModelScope.launch {
                withContext(IO) {
                    Log.println(Log.ASSERT, "CurrentPoints", currentPoints.toString())
                    val context = MyPokedexApplication.getInstance()
                    saveGameCurrentPoints(context, currentPoints)
                    saveGameHighestPoints(context, highestPoints)
                }
            }
        }
    }
}