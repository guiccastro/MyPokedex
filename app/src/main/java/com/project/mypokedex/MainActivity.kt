package com.project.mypokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.components.SinglePokemonScreen
import com.project.mypokedex.ui.theme.MyPokedexTheme

class MainActivity : ComponentActivity() {
    //private val viewModel: PokedexViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPokedexTheme {
                val viewModel: SinglePokemonScreenViewModel by viewModels()
                SinglePokemonScreen(viewModel)
            }
        }

        observeRepository()
    }

    private fun observeRepository() {
        PokemonRepository.pokemonList.observe(this) {
            //viewModel.onListUpdate(it)
        }
    }
}


