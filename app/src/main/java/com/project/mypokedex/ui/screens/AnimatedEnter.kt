package com.project.mypokedex.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.project.mypokedex.model.AnimatedEnterOrientation
import com.project.mypokedex.ui.stateholders.AnimatedEnterUIState
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.HeavyRed
import com.project.mypokedex.ui.theme.MainRed
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.White

@Composable
fun AnimatedEnter(state: AnimatedEnterUIState) {
    MainScreen(state = state)
    DownloadInformation(state = state)
}

@Composable
fun DownloadInformation(state: AnimatedEnterUIState) {
    if (state.isDownloading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                progress = state.downloadProgress,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center),
                color = BorderBlack,
                strokeWidth = 5.dp
            )

            Text(
                text = state.formattedDownloadProgress,
                color = BorderBlack,
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
    val animVisibleState = remember { MutableTransitionState(state.isDownloading) }.apply {
        targetState = state.isDownloading
    }
    if (!animVisibleState.targetState && !animVisibleState.currentState) {
        rememberSystemUiController().setSystemBarsColor(MainRed)
        rememberSystemUiController().setNavigationBarColor(MainRed)
    } else {
        rememberSystemUiController().setSystemBarsColor(HeavyRed)
        rememberSystemUiController().setNavigationBarColor(White)
    }

    AnimatedVisibility(
        visibleState = animVisibleState,
        exit = slideOutVertically(
            animationSpec = tween(2000, delayMillis = 1000),
            targetOffsetY = { -it }
        )
    ) {
        HalfScreen(AnimatedEnterOrientation.Top)
    }

    AnimatedVisibility(
        visibleState = animVisibleState,
        exit = slideOutVertically(
            animationSpec = tween(2000, delayMillis = 1000),
            targetOffsetY = { it }
        )
    ) {
        HalfScreen(AnimatedEnterOrientation.Bottom)
    }
}

@Composable
fun HalfScreen(orientation: AnimatedEnterOrientation) {
    val heightFraction = when (orientation) {
        AnimatedEnterOrientation.Top -> 0.5f
        AnimatedEnterOrientation.Bottom -> 1F
    }

    val backgroundColor = when (orientation) {
        AnimatedEnterOrientation.Top -> HeavyRed
        AnimatedEnterOrientation.Bottom -> White
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .align(alignment)
                .background(BorderBlack, RectangleShape)
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(y = circleOffset)
                .background(Color.White, CircleShape)
                .border(20.dp, BorderBlack, CircleShape)
                .align(alignment)
        )
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