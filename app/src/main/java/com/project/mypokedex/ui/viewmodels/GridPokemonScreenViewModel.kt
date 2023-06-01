package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.GridPokemonScreenStateHolder
import kotlinx.coroutines.delay
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
                showList = false,
                onScroll = { onScroll(it) },
                onSearchClick = { onSearchClick() },
                isSearching = false,
                onSearchChange = { onSearchChange(it) }
            )
        }

        viewModelScope.launch {
            PokemonRepository.pokemonList.collect {
                _uiState.value = _uiState.value.copy(
                    pokemonList = filterList(_uiState.value.searchText)
                )
            }
        }

        viewModelScope.launch {
            PokemonRepository.isRequesting.collect {
                _uiState.value = _uiState.value.copy(
                    isRequesting = it
                )
            }
        }

        viewModelScope.launch {
            delay(1000)
            _uiState.value = _uiState.value.copy(
                showList = true,
            )
        }
    }

    private fun onScroll(id: Int) {
        if (id % requestNumber == 0) {
            repeat(requestNumber + 1) {
                PokemonRepository.getPokemon(it + id)
            }
        }
    }

    private fun onSearchClick() {
        _uiState.value = _uiState.value.copy(
            isSearching = !_uiState.value.isSearching,
            searchText = "",
            pokemonList = PokemonRepository.pokemonList.value
        )
    }

    private fun onSearchChange(newText: String) {
        search(newText)
        _uiState.value = _uiState.value.copy(
            searchText = newText,
            pokemonList = filterList(newText)
        )
    }

    private fun search(text: String) {
        val id = text.toIntOrNull() ?: 0
        PokemonRepository.getPokemon(id)
        PokemonRepository.searchBasicKey(text)
    }

    private fun filterList(text: String): List<Pokemon> {
        val id = text.toIntOrNull() ?: 0
        return PokemonRepository.pokemonList.value.filter {
            it.id == id ||
                    it.name.contains(text) ||
                    it.types.toString().lowercase().contains(text)
        }
    }

}