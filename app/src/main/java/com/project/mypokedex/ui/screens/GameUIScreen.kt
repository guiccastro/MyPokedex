package com.project.mypokedex.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.project.mypokedex.ui.theme.HomeScreenCard
import com.project.mypokedex.ui.theme.MainBlue
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.theme.White

@Composable
fun GameUIScreen(state: GameScreenUIState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        PokemonImage(state = state)
        Spacer(modifier = Modifier.height(40.dp))
        OptionsButtons(state = state)
    }
}

@Composable
fun PokemonImage(state: GameScreenUIState) {
    SubcomposeAsyncImage(
        model = state.pokemon?.getGifOrImage(),
        contentDescription = null,
        modifier = Modifier
            .size(300.dp)
            .background(Brush.radialGradient(listOf(White, Color.Transparent)))
            .clickable(
                enabled = state.answered
            ) {
                state.onClickPokemon()
            },
        imageLoader = LocalContext.current.imageLoader,
        filterQuality = FilterQuality.High
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(80.dp),
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
                    colorFilter = if (!state.answered) ColorFilter.tint(BorderBlack) else null
                )
            }
        }
    }
}

@Composable
fun OptionsButtons(state: GameScreenUIState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        state.options.forEachIndexed { index, option ->
            val backgroundColor = state.buttonsUIState.getOrNull(index)?.first ?: HomeScreenCard
            val isEnabled = state.buttonsUIState.getOrNull(index)?.second ?: true
            Button(
                onClick = { state.onOptionClick(option) },
                shape = RoundedCornerShape(6.dp),
                border = BorderStroke(2.dp, BorderBlack),
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    disabledContainerColor = backgroundColor.copy(alpha = 0.5F)
                ),
                enabled = isEnabled,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ResponsiveText(
                    text = option.formattedName(),
                    fontWeight = FontWeight(800),
                    textStyle = PokemonGB,
                    targetTextSizeHeight = 16.sp,
                    color = BorderBlack
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { state.onClickNext() },
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(2.dp, BorderBlack),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainBlue,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (state.answered) 1F else 0F)
        ) {
            ResponsiveText(
                text = "NEXT",
                fontWeight = FontWeight(800),
                textStyle = PokemonGB,
                targetTextSizeHeight = 16.sp,
                color = BorderBlack
            )
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