package com.project.mypokedex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.model.BackgroundType
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.sampledata.charizard
import com.project.mypokedex.ui.CardScreen
import com.project.mypokedex.ui.components.PokemonImage
import com.project.mypokedex.ui.components.PokemonTypeToUI
import com.project.mypokedex.ui.components.ResponsiveText
import com.project.mypokedex.ui.components.RotationalImage
import com.project.mypokedex.ui.stateholders.DetailsScreenUIState
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.theme.Transparent
import com.project.mypokedex.ui.theme.White

@Composable
fun DetailsUIScreen(state: DetailsScreenUIState) {
    CardScreen {
        Column {
            state.pokemon?.let { PokemonDetails(it) }

            LazyRow {
                items(state.evolutionChain) {
                    PokemonImage(
                        url = it.getGifOrImage(),
                        backgroundType = BackgroundType.None
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonDetails(pokemon: Pokemon) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp),
        verticalArrangement = Arrangement.Center
    ) {

        // Pokemon ID
        Text(
            text = pokemon.formattedID(),
            fontSize = 14.sp,
            fontWeight = FontWeight(500),
            color = BlackTextColor,
            modifier = Modifier,
            textAlign = TextAlign.Center,
            style = PokemonGB
        )

        // Pokemon Name
        ResponsiveText(
            text = pokemon.formattedName(),
            targetTextSizeHeight = 16.sp,
            fontWeight = FontWeight(1000),
            color = BlackTextColor,
            modifier = Modifier
                .padding(vertical = 4.dp),
            textAlign = TextAlign.Center,
            textStyle = PokemonGB,
            maxLines = 1
        )

        // Pokemon Types
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            pokemon.types.forEach {
                PokemonTypeToUI(
                    pokemonType = it,
                    size = 34.dp
                )
            }
        }

        // Pokemon Images
        RotationalImage(
            frontImage = pokemon.getGifOrImage(),
            backImage = pokemon.backGif,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(CenterHorizontally),
            backgroundType = BackgroundType.RadialBackground(White.copy(alpha = 0.5F), Transparent)
        )
    }
}

@Preview
@Composable
fun DetailsUIScreenPreview() {
    MyPokedexTheme {
        Surface {
            DetailsUIScreen(DetailsScreenUIState(pokemon = charizard))
        }
    }
}