package com.project.mypokedex.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.model.AnimatedEnterOrientation
import com.project.mypokedex.ui.stateholders.AnimatedEnterUIState
import com.project.mypokedex.ui.theme.AnimatedEnterProgressIndicator
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokeballDetails
import com.project.mypokedex.ui.theme.PokeballRed
import com.project.mypokedex.ui.theme.PokeballWhite
import com.project.mypokedex.ui.theme.PokemonGB

@Composable
fun AnimatedEnter(state: AnimatedEnterUIState) {
    MainScreen(state = state)
    DownloadInformation(state = state)
}

@Composable
fun DownloadInformation(state: AnimatedEnterUIState) {
    if (state.isDownloading && state.downloadProgress != 0F) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                progress = state.downloadProgress,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center),
                color = AnimatedEnterProgressIndicator,
                strokeWidth = 5.dp
            )

            Text(
                text = state.formattedDownloadProgress,
                color = BlackTextColor,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun MainScreen(state: AnimatedEnterUIState) {
    Column {
        AnimatedScreen(state = state)
    }
}

@Composable
fun AnimatedScreen(state: AnimatedEnterUIState) {
    AnimatedVisibility(
        visible = state.isDownloading,
        exit = slideOutVertically(
            animationSpec = tween(2000, delayMillis = 1000),
            targetOffsetY = { -it }
        )
    ) {
        HalfScreen(AnimatedEnterOrientation.Top, state)
    }

    AnimatedVisibility(
        visible = state.isDownloading,
        exit = slideOutVertically(
            animationSpec = tween(2000, delayMillis = 1000),
            targetOffsetY = { it }
        )
    ) {
        HalfScreen(AnimatedEnterOrientation.Bottom, state)
    }
}

@Composable
fun HalfScreen(orientation: AnimatedEnterOrientation, state: AnimatedEnterUIState) {
    val heightFraction = when (orientation) {
        AnimatedEnterOrientation.Top -> 0.5f
        AnimatedEnterOrientation.Bottom -> 1F
    }

    val backgroundColor = when (orientation) {
        AnimatedEnterOrientation.Top -> PokeballRed
        AnimatedEnterOrientation.Bottom -> PokeballWhite
    }

    val alignment = when (orientation) {
        AnimatedEnterOrientation.Top -> Alignment.BottomCenter
        AnimatedEnterOrientation.Bottom -> Alignment.TopCenter
    }

    val circleOffset = when (orientation) {
        AnimatedEnterOrientation.Top -> 100.dp
        AnimatedEnterOrientation.Bottom -> (-100).dp
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(heightFraction)
            .background(backgroundColor)
            .clipToBounds()
    ) {

        // Line Details
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .align(alignment)
                .background(PokeballDetails, RectangleShape)
        )

        // Circle Details
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(y = circleOffset)
                .background(PokeballWhite, CircleShape)
                .border(20.dp, PokeballDetails, CircleShape)
                .align(alignment)
        )
    }

    if (orientation == AnimatedEnterOrientation.Bottom && state.isDownloading && state.downloadProgress != 0F) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 20.dp)
                .offset(y = -circleOffset)
        ) {
            Text(
                text = stringResource(id = R.string.animated_enter_download_description),
                style = PokemonGB,
                fontSize = 10.sp,
                color = BlackTextColor,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
fun AnimatedEnterPreview() {
    MyPokedexTheme {
        Surface {
            AnimatedEnter(
                state = AnimatedEnterUIState(
                    isDownloading = true,
                    downloadProgress = 0.75F,
                    formattedDownloadProgress = "75%"
                )
            )
        }
    }
}