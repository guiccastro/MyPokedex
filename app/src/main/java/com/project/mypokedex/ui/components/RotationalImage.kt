package com.project.mypokedex.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.imageLoader
import com.project.mypokedex.R
import com.project.mypokedex.model.RotationalImageSide
import com.project.mypokedex.ui.theme.MainBlack

@Composable
fun RotationalImage(frontImage: String, backImage: String, modifier: Modifier) {
    val rotationSensitivity = 0.5F
    var degrees by remember { mutableFloatStateOf(0F) }
    var side by remember { mutableStateOf(RotationalImageSide.Front) }
    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        degrees += dragAmount.x * rotationSensitivity
                        if (degrees < -275F) {
                            degrees = 90F
                        }
                        if (degrees > 275F) {
                            degrees = -90F
                        }
                        side =
                            if (degrees < 90F && degrees > -90) RotationalImageSide.Front else RotationalImageSide.Back
                        change.consume()
                    }
                )
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (side == RotationalImageSide.Front && degrees == 0F) {
                            degrees = 180F
                            side = RotationalImageSide.Back
                        } else {
                            degrees = 0F
                            side = RotationalImageSide.Front
                        }
                    }
                )
            }
            .graphicsLayer {
                rotationY = degrees
            }
    ) {
        SubcomposeAsyncImage(
            model = frontImage,
            contentDescription = null,
            modifier = modifier
                .aspectRatio(1F)
                .fillMaxSize()
                .alpha(if (side == RotationalImageSide.Front) 1F else 0F),
            imageLoader = LocalContext.current.imageLoader,
            filterQuality = FilterQuality.High
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(40.dp),
                        color = MainBlack
                    )
                }

                is AsyncImagePainter.State.Error -> {
                    Image(
                        modifier = Modifier
                            .padding(40.dp),
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MainBlack)
                    )
                }

                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }

        SubcomposeAsyncImage(
            model = backImage,
            contentDescription = null,
            modifier = modifier
                .aspectRatio(1F)
                .fillMaxSize()
                .graphicsLayer {
                    rotationY = 180F
                }
                .alpha(if (side == RotationalImageSide.Back) 1F else 0F),
            imageLoader = LocalContext.current.imageLoader,
            filterQuality = FilterQuality.High
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(40.dp),
                        color = MainBlack
                    )
                }

                is AsyncImagePainter.State.Error -> {
                    Image(
                        modifier = Modifier
                            .padding(40.dp),
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MainBlack)
                    )
                }

                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }
    }
}