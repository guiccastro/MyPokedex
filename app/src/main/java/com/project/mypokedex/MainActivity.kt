package com.project.mypokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.project.mypokedex.data.PokemonBaseInfo
import com.project.mypokedex.ui.theme.Blue
import com.project.mypokedex.ui.theme.LightGray
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.cardShape
import com.project.mypokedex.ui.theme.idShape

class MainActivity : ComponentActivity() {
    private val viewModel: PokedexViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPokedexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
                PokemonBaseList(viewModel.pokemonsList, viewModel.onClickCard)
            }
        }

        //viewModel.getPokemon(1)
        repeat(1000) {
            viewModel.getPokemon(it)
        }
    }
}

@Composable
fun PokemonBaseList(pokemons: List<PokemonBaseInfo>, onClick: (PokemonBaseInfo) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(130.dp)
    ) {
        items(pokemons) { pokemon ->
            PokemonBaseCard(pokemon, onClick)
        }
    }
}

@Composable
fun PokemonBaseCard(pokemon: PokemonBaseInfo, onClick: (PokemonBaseInfo) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .width(100.dp)
            .wrapContentHeight()
            .border(4.dp, Black, cardShape)
            .clickable {
                onClick(pokemon)
            },
        colors = CardDefaults.cardColors(containerColor = Blue),
        shape = cardShape
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = pokemon.id.toString(),
                    modifier = Modifier
                        .background(Black, idShape)
                        .padding(5.dp, 2.dp)
                        .wrapContentWidth(),
                    color = White,
                    fontSize = 8.sp
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp),
                    text = pokemon.name.firstLetterUppercase(),
                    style = typography.titleSmall,
                    color = Black,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            AsyncImage(
                model = pokemon.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(120.dp)
            )

            Text(
                text = pokemon.typesToString(),
                style = typography.bodyMedium,
                color = LightGray
            )
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyPokedexTheme {
        PokemonBaseCard()
    }
}
*/


