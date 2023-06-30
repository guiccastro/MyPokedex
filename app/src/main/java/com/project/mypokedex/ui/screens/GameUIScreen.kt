package com.project.mypokedex.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.imageLoader
import com.project.mypokedex.R
import com.project.mypokedex.sampledata.charmander
import com.project.mypokedex.sampledata.listPokemons
import com.project.mypokedex.ui.components.ResponsiveText
import com.project.mypokedex.ui.stateholders.GameScreenUIState
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB

@Composable
fun GameUIScreen(state: GameScreenUIState) {
    val pokemon = state.pokemon ?: run { return }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            model = pokemon.image,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .padding(20.dp),
            imageLoader = LocalContext.current.imageLoader,
            filterQuality = FilterQuality.High
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth(),
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
                    SubcomposeAsyncImageContent(
                        colorFilter = ColorFilter.tint(BorderBlack)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .align(CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            state.options.forEach { option ->
                Button(
                    onClick = { /*TODO*/ }
                ) {
                    ResponsiveText(
                        text = option.formattedName(),
                        fontWeight = FontWeight(400),
                        textStyle = PokemonGB
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun GameUIScreenPreview() {
    MyPokedexTheme {
        Surface {
            GameUIScreen(state = GameScreenUIState(pokemon = charmander, options = listPokemons))
        }
    }
}