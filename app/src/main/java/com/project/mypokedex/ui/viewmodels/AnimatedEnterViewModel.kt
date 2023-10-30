package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.AnimatedEnterUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class AnimatedEnterViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<AnimatedEnterUIState> =
        MutableStateFlow(AnimatedEnterUIState())
    val uiState get() = _uiState.asStateFlow()

    private val downloadProgressFormatter = DecimalFormat("#.##")

    init {
        _uiState.update {
            it.copy(
                onDownloadClick = { onDownloadClick() }
            )
        }

        viewModelScope.launch {
            repository.downloaderInfo.progressRequest.collect {
                _uiState.value = _uiState.value.copy(
                    downloadProgress = it,
                    formattedDownloadProgress = "${downloadProgressFormatter.format(it * 100)}%",
                    isDownloading = it < 1F
                )
            }
        }

        viewModelScope.launch {
            repository.downloaderInfo.needToRequestPokemons.collect { needToRequestPokemons ->
                _uiState.update {
                    it.copy(
                        showDownloadMessage = needToRequestPokemons,
                        downloadInfoType = repository.downloaderInfo.pokemonDownloadInfo
                    )
                }
            }
        }
    }

    private fun onDownloadClick() {
        viewModelScope.launch {
            repository.downloaderInfo.requestPokemons(
                repository.getBasicKeys(),
                repository.pokemonList.value,
                repository.getTotalPokemons()
            )
        }

        _uiState.update {
            it.copy(
                showDownloadMessage = false
            )
        }
    }
}