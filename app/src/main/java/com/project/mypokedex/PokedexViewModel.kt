package com.project.mypokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.data.PokemonBaseInfo
import com.project.mypokedex.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokedexViewModel: ViewModel() {

    var pokemonsList = PokemonRepository.pokemonList

    val onClickCard: (PokemonBaseInfo) -> Unit = {
        getPokemon(it.id + 1)
    }

    init {
        PokemonRepository
    }

    fun getPokemon(id: Int): PokemonBaseInfo? {
        var response: PokemonBaseInfo? = null
        viewModelScope.launch(Dispatchers.IO) {
            response = PokemonRepository.getPokemon(id)
        }
        return response
    }
}