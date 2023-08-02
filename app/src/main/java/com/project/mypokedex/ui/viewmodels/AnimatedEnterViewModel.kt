package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.AnimatedEnterUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimatedEnterViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _animatedEnterUIState: MutableStateFlow<AnimatedEnterUIState> =
        MutableStateFlow(AnimatedEnterUIState())
    val animatedEnterUIState get() = _animatedEnterUIState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.canOpenApp.collect {
                _animatedEnterUIState.value = _animatedEnterUIState.value.copy(
                    canShowApp = it
                )
            }
        }
    }
}