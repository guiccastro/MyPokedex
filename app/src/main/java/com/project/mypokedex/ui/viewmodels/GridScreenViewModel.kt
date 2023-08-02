package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.navigation.MainNavComponent
import com.project.mypokedex.navigation.screens.DetailsScreen
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.GridScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GridScreenViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<GridScreenUIState> =
        MutableStateFlow(GridScreenUIState())
    val uiState get() = _uiState.asStateFlow()

    private val pokemonsToRequest = 20

    init {
        _uiState.update { currentState ->
            currentState.copy(
                showList = false,
                isSearching = false,
                onSearchChange = { onSearchChange(it) },
                onPokemonClick = { onPokemonClick(it) }
            )
        }

        getPokemonList(0, pokemonsToRequest)

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                showList = true,
            )
        }
    }

    private fun getPokemonList(initialId: Int, count: Int) {
        viewModelScope.launch {
            repository.getPokemonIdList(initialId, count).forEach { id ->
                async {
                    val pokemon = repository.getPokemon(id)
                    _uiState.value = _uiState.value.copy(
                        pokemonList = (_uiState.value.pokemonList + pokemon).sortedBy { it.id }
                    )
                }
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
            pokemonList = emptyList(), //repository.pokemonList.value,
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
        return emptyList()
//        return repository.pokemonList.value.filter {
//            it.id == id ||
//                    it.name.contains(text) ||
//                    it.types.toString().lowercase().contains(text)
//        }
    }

}