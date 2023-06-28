package com.project.mypokedex.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.project.mypokedex.ui.stateholders.AnimatedEnterUIState
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.HeavyRed
import com.project.mypokedex.ui.theme.MainRed

@Composable
fun AnimatedEnter(state: AnimatedEnterUIState) {
    val animVisibleState = remember { MutableTransitionState(state.isDownloading) }.apply {
        targetState = state.isDownloading
    }
    if (!animVisibleState.targetState && !animVisibleState.currentState) {
        rememberSystemUiController().setSystemBarsColor(MainRed)
        rememberSystemUiController().setNavigationBarColor(MainRed)
    } else {
        rememberSystemUiController().setSystemBarsColor(HeavyRed)
        rememberSystemUiController().setNavigationBarColor(Color.White)
    }

    Column {
        AnimatedVisibility(
            visibleState = animVisibleState,
            exit = slideOutVertically(
                animationSpec = tween(2000, delayMillis = 1000),
                targetOffsetY = { -it }
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .background(HeavyRed)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .background(BorderBlack, RectangleShape)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                verticalArrangement = Arrangement.Bottom
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .offset(y = 100.dp)
                            .background(Color.White, CircleShape)
                            .border(20.dp, BorderBlack, CircleShape)
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = state.isDownloading,
            exit = slideOutVertically(
                animationSpec = tween(2000, delayMillis = 1000),
                targetOffsetY = { it },
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(BorderBlack, RectangleShape)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clipToBounds(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .offset(y = (-100).dp)
                        .background(Color.White, CircleShape)
                        .border(20.dp, BorderBlack, CircleShape)
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if (state.isDownloading) {
                CircularProgressIndicator(
                    progress = state.downloadProgress,
                    modifier = Modifier
                        .size(100.dp),
                    color = BorderBlack,
                    strokeWidth = 5.dp
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if (state.isDownloading) {
                Text(
                    text = state.formattedDownloadProgress,
                    color = BorderBlack
                )
            }
        }
    }
}