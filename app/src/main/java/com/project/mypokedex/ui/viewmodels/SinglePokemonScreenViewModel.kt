package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.SinglePokemonScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class SinglePokemonScreenViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<SinglePokemonScreenUIState> =
        MutableStateFlow(SinglePokemonScreenUIState())
    val uiState get() = _uiState.asStateFlow()

    private val formatter = DecimalFormat("#")

    init {
        PokemonRepository.getPokemon(1)
        _uiState.update { currentState ->
            currentState.copy(
                onSearchChange = {
                    onSearchChange(it)
                }
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

    private fun onSearchChange(newText: String) {
        val newSearch = try {
            formatter.format(newText.toInt())
        } catch (e: IllegalArgumentException) {
            if (newText.isEmpty()) {
                newText
            } else {
                null
            }
        }

        newSearch?.let {
            _uiState.value = _uiState.value.copy(
                searchText = newSearch
            )
        }

        searchPokemonById(newSearch?.toIntOrNull())
    }

    private fun searchPokemonById(id: Int?) {
        id?.let {
            PokemonRepository.getPokemon(id)
        }
    }
}