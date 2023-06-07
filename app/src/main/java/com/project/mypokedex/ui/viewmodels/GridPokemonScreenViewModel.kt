package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.GridPokemonScreenStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class GridPokemonScreenViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<GridPokemonScreenStateHolder> =
        MutableStateFlow(GridPokemonScreenStateHolder())
    val uiState get() = _uiState.asStateFlow()

    private val downloadProgressFormatter = DecimalFormat("#.##")

    init {
        _uiState.update { currentState ->
            currentState.copy(
                showList = false,
                onSearchClick = { onSearchClick() },
                isSearching = false,
                onSearchChange = { onSearchChange(it) }
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
            repository.progressRequest.collect {
                _uiState.value = _uiState.value.copy(
                    downloadProgress = it,
                    formattedDownloadProgress = "${downloadProgressFormatter.format(it * 100)}%",
                    isDownloading = it < 1F
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
            isSearching = !_uiState.value.isSearching,
            searchText = "",
            pokemonList = repository.pokemonList.value
        )
    }

    private fun onSearchChange(newText: String) {
        _uiState.value = _uiState.value.copy(
            searchText = newText,
            pokemonList = filterList(newText)
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