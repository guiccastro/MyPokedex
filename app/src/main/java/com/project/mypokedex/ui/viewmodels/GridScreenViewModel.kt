package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.navigation.MainNavComponent
import com.project.mypokedex.navigation.screens.DetailsScreen
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.GridScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GridScreenViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<GridScreenUIState> =
        MutableStateFlow(GridScreenUIState())
    val uiState get() = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                isSearching = false,
                onSearchChange = { onSearchChange(it) },
                onPokemonClick = { onPokemonClick(it) }
            )
        }

        viewModelScope.launch {
            PokemonRepository.pokemonList.collect {
                _uiState.value = _uiState.value.copy(
                    pokemonList = filterList(_uiState.value.searchText)
                )
            }
        }
    }

    private fun onPokemonClick(pokemon: Pokemon) {
        MainNavComponent.navController.apply {
            DetailsScreen.apply {
                navigateToItself(pokemonId = pokemon.id)
            }
        }
    }

    fun onSearchClick() {
        _uiState.value = _uiState.value.copy(
            pokemonList = PokemonRepository.pokemonList.value,
            isSearching = !_uiState.value.isSearching,
            searchText = ""
        )
    }

    private fun onSearchChange(newText: String) {
        _uiState.value = _uiState.value.copy(
            pokemonList = filterList(newText),
            searchText = newText
        )
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