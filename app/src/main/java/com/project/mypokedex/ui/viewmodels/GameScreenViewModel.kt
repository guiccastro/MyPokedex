package com.project.mypokedex.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.GameScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<GameScreenUIState> =
        MutableStateFlow(GameScreenUIState())
    val uiState get() = _uiState.asStateFlow()

    private fun List<Pokemon>.getPokemon(): Pokemon? {
        if (isEmpty()) return null
        return this[indices.random()]
    }

    init {
        setGame()
    }

    private fun setGame() {
        viewModelScope.launch {
            val options = repository.getRandomPokemons(3)
            _uiState.value = _uiState.value.copy(
                options = options,
                pokemon = options.getPokemon()
            )
        }
    }
}