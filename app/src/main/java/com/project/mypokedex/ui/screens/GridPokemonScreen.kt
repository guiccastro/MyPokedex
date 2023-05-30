package com.project.mypokedex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.mypokedex.sampledata.listPokemons
import com.project.mypokedex.ui.components.Background
import com.project.mypokedex.ui.components.PokemonGridCard
import com.project.mypokedex.ui.stateholders.GridPokemonScreenStateHolder
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.viewmodels.GridPokemonScreenViewModel


@Composable
fun GridPokemonScreen(viewModel: GridPokemonScreenViewModel) {
    val state = viewModel.uiState.collectAsState().value
    GridPokemonScreen(state = state)
}

@Composable
fun GridPokemonScreen(state: GridPokemonScreenStateHolder = GridPokemonScreenStateHolder()) {
    Background()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(6.dp)
    ) {
        items(state.pokemonList) {
            state.onScroll(it.id)
            PokemonGridCard(pokemon = it)
        }
    }
}

@Preview
@Composable
fun GridPokemonScreenPreview() {
    MyPokedexTheme {
        Surface {
            GridPokemonScreen(GridPokemonScreenStateHolder(pokemonList = listPokemons))
        }
    }
}