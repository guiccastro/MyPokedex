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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.mypokedex.model.AnimatedEnterOrientation
import com.project.mypokedex.ui.stateholders.AnimatedEnterUIState
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokeballDetails
import com.project.mypokedex.ui.theme.PokeballRed
import com.project.mypokedex.ui.theme.PokeballWhite

@Composable
fun AnimatedEnter(state: AnimatedEnterUIState, content: @Composable () -> Unit = {}) {
    if (state.canShowApp) {
        content()
    }
    MainScreen(state = state)
}

@Composable
fun MainScreen(state: AnimatedEnterUIState) {
    Column {
        AnimatedScreen(state = state)
    }
}

@Composable
fun AnimatedScreen(state: AnimatedEnterUIState) {
    val animVisibleState = remember { MutableTransitionState(true) }
        .apply { targetState = !state.canShowApp }

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
}

@Preview
@Composable
fun AnimatedEnterPreview() {
    MyPokedexTheme {
        Surface {
            AnimatedEnter(
                state = AnimatedEnterUIState(
                    canShowApp = false
                )
            )
        }
    }
}