package com.project.mypokedex

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypokedex.data.PokemonInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokedexViewModel: ViewModel() {

    private val pokemonClient = PokemonClient.getClient()

    val pokemonsList = mutableStateListOf<PokemonInfo>()

    init {
        repeat(151) {
            viewModelScope.launch(Dispatchers.IO) {
                requestPokemon(it)
            }
        }
    }

    private fun requestPokemon(id: Int) {
        pokemonClient.getPokemon(id).enqueue(object : Callback<PokemonInfo> {
            override fun onFailure(call: Call<PokemonInfo>, t: Throwable) {
            }

            override fun onResponse(call: Call<PokemonInfo>, response: Response<PokemonInfo>) {
                response.body()?.let { pokemon ->
                    pokemonsList.add(pokemon)
                    pokemonsList.sortBy { it.id }

                }
            }
        })
    }
}