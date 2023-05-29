package com.project.mypokedex.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.sampledata.charizard
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.theme.getImageLoader

@Composable
fun PokemonSingleCard(pokemon: Pokemon) {
    Row(modifier = Modifier.padding(horizontal = 6.dp)) {
        Box(modifier = Modifier.size(150.dp)) {
            AsyncImage(
                model = pokemon.gif,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(4.dp),
                imageLoader = getImageLoader(),
                filterQuality = FilterQuality.High
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Column {
                Text(
                    text = pokemon.formattedID(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = Color.DarkGray,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = PokemonGB
                )
                Text(
                    text = pokemon.formattedName(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color.DarkGray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = PokemonGB,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items(pokemon.types) {
                    PokemonTypeToUI(pokemonType = it, fontSize = 12.sp)
                }
            }
        }

    }
}

@Preview
@Composable
fun PokemonCardPreview() {
    PokemonSingleCard(charizard)
}