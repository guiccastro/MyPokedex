package com.project.mypokedex.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.project.mypokedex.ui.components.PokemonTypeToUI
import com.project.mypokedex.ui.components.ResponsiveText
import com.project.mypokedex.ui.components.RotationalImage
import com.project.mypokedex.ui.innerShadow
import com.project.mypokedex.ui.stateholders.DetailsScreenUIState
import com.project.mypokedex.ui.theme.Black
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.CardColor
import com.project.mypokedex.ui.theme.MainBlue
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.theme.Transparent
import com.project.mypokedex.ui.theme.White

@Composable
fun DetailsUIScreen(state: DetailsScreenUIState) {
    val externalCorner = 8.dp
    val internalCorner = 6.dp
    val externalShape = RoundedCornerShape(externalCorner)
    val internalShape = RoundedCornerShape(internalCorner)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp),
        color = CardColor,
        shape = externalShape
    ) {
        Surface(
            modifier = Modifier
                .padding(all = 10.dp)
                .innerShadow(
                    color = Black,
                    cornersRadius = internalCorner,
                    blur = 5.dp
                ),
            color = MainBlue,
            shape = internalShape
        ) {
            state.pokemon?.let { PokemonDetails(it) }
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
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = PokemonGB
        )

        // Pokemon Name
        ResponsiveText(
            text = pokemon.formattedName(),
            targetTextSizeHeight = 14.sp,
            fontWeight = FontWeight(400),
            color = BlackTextColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp, bottom = 4.dp),
            textAlign = TextAlign.Center,
            textStyle = PokemonGB,
            maxLines = 1
        )

        // Pokemon Types
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            pokemon.types.forEach {
                PokemonTypeToUI(pokemonType = it, fontSize = 12.sp)
            }
        }

        // Pokemon Images
        RotationalImage(
            frontImage = pokemon.gif,
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