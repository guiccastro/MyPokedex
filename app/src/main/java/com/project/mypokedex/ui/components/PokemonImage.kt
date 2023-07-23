package com.project.mypokedex.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.min
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.imageLoader
import com.project.mypokedex.R
import com.project.mypokedex.model.BackgroundType
import com.project.mypokedex.ui.extensions.clickableOrNull
import com.project.mypokedex.ui.extensions.verifyBackgroundTypeImage
import com.project.mypokedex.ui.extensions.verifyBackgroundTypeRadial
import com.project.mypokedex.ui.theme.MainBlack

@Composable
fun PokemonImage(
    url: String?,
    modifier: Modifier = Modifier,
    clickable: Boolean? = null,
    onClick: () -> Unit = {},
    backgroundType: BackgroundType,
    imageColorFilter: ColorFilter? = null
) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .aspectRatio(1F)
            .clickableOrNull(clickable, onClick)
            .then(modifier)
            .verifyBackgroundTypeRadial(backgroundType),
        imageLoader = LocalContext.current.imageLoader,
        filterQuality = FilterQuality.High
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize(0.2F)
                            .align(Alignment.Center),
                        color = MainBlack,
                        strokeWidth = min(maxWidth, maxHeight) * 0.02F
                    )
                }
            }

            is AsyncImagePainter.State.Error -> {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize(0.2F)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MainBlack)
                    )
                }
            }

            else -> {
                if (backgroundType != BackgroundType.None && backgroundType !is BackgroundType.RadialBackground) {
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_pokeball),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(0.8F)
                                .align(Alignment.Center)
                                .verifyBackgroundTypeImage(backgroundType)
                        )
                    }
                }

                SubcomposeAsyncImageContent(
                    colorFilter = imageColorFilter
                )
            }
        }
    }
}