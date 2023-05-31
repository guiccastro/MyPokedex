package com.project.mypokedex.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.sampledata.listPokemons
import com.project.mypokedex.ui.components.Background
import com.project.mypokedex.ui.components.PokemonGridCard
import com.project.mypokedex.ui.components.bottomBorder
import com.project.mypokedex.ui.components.innerShadow
import com.project.mypokedex.ui.stateholders.GridPokemonScreenStateHolder
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.BorderBlackShadow
import com.project.mypokedex.ui.theme.HomeScreenCard
import com.project.mypokedex.ui.theme.MainBlue
import com.project.mypokedex.ui.theme.MainRed
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.viewmodels.GridPokemonScreenViewModel


@Composable
fun GridPokemonScreen(viewModel: GridPokemonScreenViewModel) {
    val state = viewModel.uiState.collectAsState().value
    GridPokemonScreen(state = state)
}

@Composable
fun GridPokemonScreen(state: GridPokemonScreenStateHolder = GridPokemonScreenStateHolder()) {
    Background()

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .bottomBorder((0.3).dp, BorderBlack),
                shadowElevation = 10.dp,
                color = MainRed
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "MyPokedex",
                        style = PokemonGB,
                        color = BorderBlack,
                        fontSize = 14.sp
                    )
                }
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(vertical = 20.dp, horizontal = 10.dp)
                .border((0.3).dp, BorderBlackShadow, RoundedCornerShape(16.dp)),
            shadowElevation = 10.dp,
            color = HomeScreenCard,
            shape = RoundedCornerShape(16.dp)
        ) {
            Surface(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 5.dp)
                    .innerShadow(
                        color = Color.Black,
                        cornersRadius = 14.dp,
                        blur = 5.dp
                    ),
                color = MainBlue,
                shape = RoundedCornerShape(14.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(vertical = 10.dp, horizontal = 6.dp)
                ) {
                    items(state.pokemonList) { pokemon ->
                        state.onScroll(pokemon.id)
                        PokemonGridCard(pokemon = pokemon)
                    }
                }
            }
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