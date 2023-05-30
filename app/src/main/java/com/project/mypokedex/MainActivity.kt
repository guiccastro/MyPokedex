package com.project.mypokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.project.mypokedex.ui.viewmodels.GridPokemonScreenViewModel
import com.project.mypokedex.ui.screens.GridPokemonScreen
import com.project.mypokedex.ui.theme.MyPokedexTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPokedexTheme {
                //val viewModel: SinglePokemonScreenViewModel by viewModels()
                //SinglePokemonScreen(viewModel)\

                val viewModel: GridPokemonScreenViewModel by viewModels()
               GridPokemonScreen(viewModel = viewModel)
            }
        }
    }
}


