package com.project.mypokedex.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import com.project.mypokedex.ui.backgroundOrNull
import com.project.mypokedex.ui.clickableOrNull
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.Transparent
import com.project.mypokedex.ui.theme.White

@Composable
fun PokemonImage(
    url: String?,
    modifier: Modifier = Modifier,
    clickable: Boolean? = null,
    onClick: () -> Unit = {},
    background: Brush? = Brush.radialGradient(listOf(White.copy(alpha = 0.5F), Transparent)),
    imageColorFilter: ColorFilter? = null
) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .aspectRatio(1F)
            .backgroundOrNull(background)
            .clickableOrNull(clickable, onClick)
            .then(modifier),
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
                SubcomposeAsyncImageContent(
                    colorFilter = imageColorFilter
                )
            }
        }
    }


}