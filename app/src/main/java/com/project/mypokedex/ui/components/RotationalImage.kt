package com.project.mypokedex.ui.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import com.project.mypokedex.model.BackgroundType
import com.project.mypokedex.model.RotationalImageSide

@Composable
fun RotationalImage(
    frontImage: String,
    backImage: String,
    modifier: Modifier,
    backgroundType: BackgroundType = BackgroundType.None
) {
    val rotationSensitivity = 0.5F
    var degrees by remember { mutableFloatStateOf(0F) }
    var side by remember { mutableStateOf(RotationalImageSide.Front) }

    val canRotate = frontImage.isNotBlank() && backImage.isNotBlank()
    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        if (canRotate) {
                            degrees += dragAmount.x * rotationSensitivity
                            if (degrees < -275F) {
                                degrees = 90F
                            }
                            if (degrees > 275F) {
                                degrees = -90F
                            }
                            side =
                                if (degrees < 90F && degrees > -90) RotationalImageSide.Front else RotationalImageSide.Back
                        }
                        change.consume()
                    }
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (canRotate) {
                            if (degrees == 0F || degrees == 180F) {
                                if (side == RotationalImageSide.Front) {
                                    degrees = 180F
                                    side = RotationalImageSide.Back
                                } else {
                                    degrees = 0F
                                    side = RotationalImageSide.Front
                                }
                            } else {
                                degrees = if (side == RotationalImageSide.Front) {
                                    0F
                                } else {
                                    180F
                                }
                            }
                        }
                    }
                )
            }
            .graphicsLayer {
                rotationY = degrees
            }
    ) {
        PokemonImage(
            url = frontImage,
            modifier = modifier
                .aspectRatio(1F)
                .fillMaxSize()
                .alpha(if (side == RotationalImageSide.Front) 1F else 0F),
            backgroundType = backgroundType,
            clickable = null
        )

        PokemonImage(
            url = backImage,
            modifier = modifier
                .aspectRatio(1F)
                .fillMaxSize()
                .graphicsLayer {
                    rotationY = 180F
                }
                .alpha(if (side == RotationalImageSide.Back) 1F else 0F),
            backgroundType = backgroundType,
            clickable = null
        )
    }
}