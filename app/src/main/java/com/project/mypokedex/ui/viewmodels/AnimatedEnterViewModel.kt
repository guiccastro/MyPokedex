package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.AnimatedEnterUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnimatedEnterViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<AnimatedEnterUIState> =
        MutableStateFlow(AnimatedEnterUIState())
    val uiState get() = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                onDownloadClick = { onDownloadClick() }
            )
        }

        viewModelScope.launch {
            PokemonRepository.downloaderInfo.needToRequestPokemons.collect { needToRequestPokemons ->
                if (needToRequestPokemons != null) {
                    _uiState.update {
                        it.copy(
                            showDownloadMessage = needToRequestPokemons,
                            downloadInfoType = PokemonRepository.downloaderInfo.pokemonDownloadInfo,
                            downloadNewProperties = PokemonRepository.downloaderInfo.pokemonPropertiesDesc,
                            openApp = !needToRequestPokemons
                        )
                    }
                }
            }
        }
    }

    private fun onDownloadClick() {
        viewModelScope.launch {
            PokemonRepository.downloaderInfo.requestPokemons(
                PokemonRepository.getBasicKeys(),
                PokemonRepository.pokemonList.value,
                PokemonRepository.getTotalPokemons()
            )
        }

        _uiState.update {
            it.copy(
                showDownloadMessage = false,
                openApp = true
            )
        }
    }
}