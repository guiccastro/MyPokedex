package com.project.mypokedex

import androidx.lifecycle.ViewModel
import com.project.mypokedex.model.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.DecimalFormat

data class SinglePokemonScreenUIState(
    val currentPokemon: Pokemon = Pokemon(0, "", emptyList(), "", ""),
    val searchText: String = "",
    val onSearchChange: (String) -> Unit = {},
    val onClickUp: () -> Unit = {},
    val onClickDown: () -> Unit = {},
    val onClickLeft: () -> Unit = {},
    val onClickRight: () -> Unit = {}
)

class SinglePokemonScreenViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<SinglePokemonScreenUIState> =
        MutableStateFlow(SinglePokemonScreenUIState())
    val uiState get() = _uiState.asStateFlow()

    private val formatter = DecimalFormat("#")

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onSearchChange = {
                    onSearchChange(it)
                }
            )
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
        // TODO: Implementar busca
    }
}