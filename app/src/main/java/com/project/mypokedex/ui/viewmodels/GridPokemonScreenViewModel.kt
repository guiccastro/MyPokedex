package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.GridPokemonScreenStateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GridPokemonScreenViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<GridPokemonScreenStateHolder> =
        MutableStateFlow(GridPokemonScreenStateHolder())
    val uiState get() = _uiState.asStateFlow()

    private val requestNumber = 20

    init {
        repeat(requestNumber + 1) {
            PokemonRepository.getPokemon(it)
        }

        _uiState.update { currentState ->
            currentState.copy(
                onScroll = { onScroll(it) }
            )
        }

        viewModelScope.launch {
            PokemonRepository.pokemonList.collect {
                _uiState.value = _uiState.value.copy(
                    pokemonList = it
                )
            }
        }
    }

    private fun onScroll(id: Int) {
        if (id % requestNumber == 0) {
            repeat(requestNumber + 1) {
                PokemonRepository.getPokemon(it + id)
            }
        }
    }

}