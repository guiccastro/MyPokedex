package com.project.mypokedex.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.navigation.MainNavComponent
import com.project.mypokedex.navigation.MainNavComponent.Companion.getSingleTopWithPopUpTo
import com.project.mypokedex.navigation.screens.DetailsScreen
import com.project.mypokedex.navigation.screens.DetailsScreen.pokemonIdArgument
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.stateholders.DetailsScreenUIState
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

    private val _uiState: MutableStateFlow<DetailsScreenUIState> =
        MutableStateFlow(DetailsScreenUIState())
    val uiState get() = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                onPokemonClick = { pokemon ->
                    MainNavComponent.navController.apply {
                        val lastId =
                            this.currentBackStackEntry?.arguments?.getInt(pokemonIdArgument)
                        DetailsScreen.apply {
                            val navOptions =
                                if (pokemon.id == lastId) getSingleTopWithPopUpTo(getRoute()) else navOptions {
                                    popBackStack()
                                }
                            navigateToItself(pokemonId = pokemon.id, navOptions = navOptions)
                        }
                    }
                }
            )
        }

        CoroutineScope(IO).launch {
            savedStateHandle
                .getStateFlow<Int?>(pokemonIdArgument, null)
                .filterNotNull()
                .collect { id ->
                    repository.getPokemon(id)?.let {
                        setPokemon(it)
                        setEvolutionChain(it)
                        getSpecies(it)
                    }
                }
        }
    }

    private fun setPokemon(pokemon: Pokemon) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    pokemon = pokemon
                )
            }
        }
    }

    private fun setEvolutionChain(pokemon: Pokemon) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    evolutionChain = repository.getEvolutionChainByPokemon(pokemon)
                )
            }
        }
    }

    private fun getSpecies(pokemon: Pokemon) {
        viewModelScope.launch {
            _uiState.update {
                val varieties =
                    repository.getSpecies(pokemon).varieties.mapNotNull { pokemonVarieties ->
                        if (pokemonVarieties == pokemon) null else pokemonVarieties
                    }
                it.copy(
                    varieties = varieties
                )
            }
        }
    }
}