package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.R
import com.project.mypokedex.TopAppBarItem
import com.project.mypokedex.TopAppBarStateHolder
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.gridscreen.GridScreenStateHolder
import com.project.mypokedex.ui.theme.BorderBlack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GridPokemonScreenViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<GridScreenStateHolder> =
        MutableStateFlow(GridScreenStateHolder())
    val uiState get() = _uiState.asStateFlow()

    private val _topBarState: MutableStateFlow<TopAppBarStateHolder> =
        MutableStateFlow(TopAppBarStateHolder())
    val topBarState get() = _topBarState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                showList = false,
                isSearching = false,
                onSearchChange = { onSearchChange(it) }
            )
        }

        _topBarState.update { currentState ->
            currentState.copy(
                itemsList = listOf(
                    TopAppBarItem(
                        icon = R.drawable.ic_search,
                        iconColor = BorderBlack,
                        onClickEvent = { onSearchClick() }
                    )
                )
            )
        }
        viewModelScope.launch {
            repository.pokemonList.collect {
                _uiState.value = _uiState.value.copy(
                    pokemonList = filterList(_uiState.value.searchText)
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

    private fun onSearchClick() {
        _uiState.value = _uiState.value.copy(
            pokemonList = repository.pokemonList.value,
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
        return repository.pokemonList.value.filter {
            it.id == id ||
                    it.name.contains(text) ||
                    it.types.toString().lowercase().contains(text)
        }
    }

}