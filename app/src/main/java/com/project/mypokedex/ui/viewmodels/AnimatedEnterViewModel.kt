package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.AnimatedEnterStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class AnimatedEnterViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _animatedEnterStateHolder: MutableStateFlow<AnimatedEnterStateHolder> =
        MutableStateFlow(AnimatedEnterStateHolder())
    val animatedEnterStateHolder get() = _animatedEnterStateHolder.asStateFlow()

    private val downloadProgressFormatter = DecimalFormat("#.##")

    init {
        viewModelScope.launch {
            repository.progressRequest.collect {
                _animatedEnterStateHolder.value = _animatedEnterStateHolder.value.copy(
                    downloadProgress = it,
                    formattedDownloadProgress = "${downloadProgressFormatter.format(it * 100)}%",
                    isDownloading = it < 1F
                )
            }
        }
    }
}