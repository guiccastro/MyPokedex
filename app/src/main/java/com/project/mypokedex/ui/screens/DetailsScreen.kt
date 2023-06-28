package com.project.mypokedex.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.imageLoader
import com.project.mypokedex.R
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.sampledata.charizard
import com.project.mypokedex.ui.components.PokemonTypeToUI
import com.project.mypokedex.ui.components.ResponsiveText
import com.project.mypokedex.ui.components.customShadow
import com.project.mypokedex.ui.components.innerShadow
import com.project.mypokedex.ui.stateholders.DetailsScreenStateHolder
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.BorderBlackShadow
import com.project.mypokedex.ui.theme.HomeScreenCard
import com.project.mypokedex.ui.theme.MainBlue
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB

@Composable
fun DetailsScreen(state: DetailsScreenStateHolder) {
    val externalCorner = 8.dp
    val internalCorner = 6.dp
    val externalShape = RoundedCornerShape(externalCorner)
    val internalShape = RoundedCornerShape(internalCorner)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp)
            .border((0.3).dp, BorderBlackShadow, externalShape)
            .customShadow(
                color = Color.Black.copy(alpha = 0.7f),
                blurRadius = 5.dp,
                borderRadius = externalCorner
            ),
        color = HomeScreenCard,
        shape = externalShape
    ) {
        Surface(
            modifier = Modifier
                .padding(all = 10.dp)
                .innerShadow(
                    color = Color.Black,
                    cornersRadius = internalCorner,
                    blur = 5.dp
                ),
            color = MainBlue,
            shape = internalShape
        ) {
            PokemonDetails(state.pokemon)
        }
    }
}

@Composable
fun PokemonDetails(pokemon: Pokemon?) {
    if (pokemon == null) {
        return
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = pokemon.formattedID(),
                fontSize = 14.sp,
                fontWeight = FontWeight(500),
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = PokemonGB
            )
            ResponsiveText(
                text = pokemon.formattedName(),
                targetTextSizeHeight = 14.sp,
                fontWeight = FontWeight(400),
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 4.dp),
                textAlign = TextAlign.Center,
                textStyle = PokemonGB,
                maxLines = 1
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                pokemon.types.forEach {
                    PokemonTypeToUI(pokemonType = it, fontSize = 12.sp)
                }
            }
            SubcomposeAsyncImage(
                model = pokemon.getGifOrImage(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(CenterHorizontally),
                imageLoader = LocalContext.current.imageLoader,
                filterQuality = FilterQuality.High
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(40.dp),
                            color = BorderBlack
                        )
                    }

                    is AsyncImagePainter.State.Error -> {
                        Image(
                            modifier = Modifier
                                .padding(40.dp),
                            painter = painterResource(id = R.drawable.ic_error),
                            contentDescription = "Error",
                            colorFilter = ColorFilter.tint(BorderBlack)
                        )
                    }

                    else -> {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailsPokemonScreenPreview() {
    MyPokedexTheme {
        Surface {
            DetailsScreen(DetailsScreenStateHolder(pokemon = charizard))
        }
    }
}