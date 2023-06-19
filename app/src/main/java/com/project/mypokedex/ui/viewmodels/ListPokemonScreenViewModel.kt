package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.ListPokemonScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class ListPokemonScreenViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ListPokemonScreenUIState> =
        MutableStateFlow(ListPokemonScreenUIState())
    val uiState get() = _uiState.asStateFlow()

    private val formatter = DecimalFormat("#")

    init {
        repository.getPokemon(1)
        _uiState.update { currentState ->
            currentState.copy(
                onSearchChange = {
                    onSearchChange(it)
                }
            )
        }

        viewModelScope.launch {
            repository.pokemonList.collect {
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
            repository.getPokemon(id)
        }
    }
}