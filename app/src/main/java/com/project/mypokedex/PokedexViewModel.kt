package com.project.mypokedex

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.project.mypokedex.data.Pokemon
import com.project.mypokedex.repository.PokemonRepository

class PokedexViewModel: ViewModel() {

    var pokemonsList = PokemonRepository.pokemonList.value

    var currentPokemonID = 0
        set(value) {
            field = value
            currentPokemonInfo = getPokemon(currentPokemonID)
        }

    var currentPokemonInfo: Pokemon? by mutableStateOf(null)
        private set

    val onClickCard: (Pokemon) -> Unit = {
        getPokemon(it.id + 1)
    }

    val onClickPrevious: () -> Unit = {
        if (currentPokemonID > 1) {
            currentPokemonID -= 1
        }
    }

    val onClickNext: () -> Unit = {
        currentPokemonID += 1
    }

    init {
        PokemonRepository
        currentPokemonID = 1
    }

    fun getPokemon(id: Int): Pokemon? {
        return PokemonRepository.getPokemon(id)
    }

    fun onListUpdate(list: SnapshotStateList<Pokemon>) {
        if (currentPokemonInfo == null) {
            list.find { it.id == currentPokemonID }?.let {
                currentPokemonInfo = it
            }
        }
    }
}