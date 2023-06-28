package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.navigation.destinations.pokemonIdArgument
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.DetailsScreenStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PokemonRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailsScreenStateHolder> =
        MutableStateFlow(DetailsScreenStateHolder())
    val uiState get() = _uiState.asStateFlow()

    init {
        CoroutineScope(IO).launch {
            savedStateHandle
                .getStateFlow<Int?>(pokemonIdArgument, null)
                .filterNotNull()
                .collect { id ->
                    repository.getPokemon(id)?.let {
                        updateUiState(it)
                    }
                }
        }
    }

    private fun updateUiState(pokemon: Pokemon) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    pokemon = pokemon
                )
            }
        }
    }
}