package com.project.mypokedex.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.model.AnimatedEnterOrientation
import com.project.mypokedex.model.downloadInfo.DownloadType
import com.project.mypokedex.model.downloadInfo.UpdateInfo
import com.project.mypokedex.ui.components.ResponsiveText
import com.project.mypokedex.ui.stateholders.AnimatedEnterUIState
import com.project.mypokedex.ui.theme.AnimatedEnterProgressIndicator
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainRed
import com.project.mypokedex.ui.theme.MainWhite
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokeballDetails
import com.project.mypokedex.ui.theme.PokeballRed
import com.project.mypokedex.ui.theme.PokeballWhite
import com.project.mypokedex.ui.theme.PokemonGB

@Composable
fun AnimatedEnter(state: AnimatedEnterUIState) {
    MainScreen(state = state)
    DownloadInformation(state = state)
    DownloadMessage(state = state)
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

    Surface {
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

    if (orientation == AnimatedEnterOrientation.Top) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height((LocalConfiguration.current.screenHeightDp / 2).dp - circleOffset)
                .padding(vertical = 20.dp, horizontal = 20.dp)
        ) {
            ResponsiveText(
                text = stringResource(id = R.string.app_name),
                textStyle = PokemonGB,
                targetTextSizeHeight = 26.sp,
                color = BlackTextColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(MainWhite, RoundedCornerShape(6.dp))
                    .border(4.dp, MainBlack, RoundedCornerShape(6.dp))
                    .padding(vertical = 10.dp, horizontal = 20.dp)
            )
        }
    }
}

@Composable
fun DownloadMessage(state: AnimatedEnterUIState) {
    if (state.showDownloadMessage) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .background(MainWhite, RoundedCornerShape(10.dp))
                    .border(1.dp, MainBlack, RoundedCornerShape(10.dp))
                    .padding(20.dp)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_download),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MainBlack),
                    modifier = Modifier
                        .size(30.dp)
                )

                Text(
                    text = stringResource(id = R.string.animated_enter_message_title),
                    style = PokemonGB,
                    color = BlackTextColor,
                    fontSize = 14.sp
                )

                Text(
                    text = stringResource(id = if (state.downloadInfoType == DownloadType.FullInfo) R.string.animated_enter_message_full_info else R.string.animated_enter_message_update),
                    style = PokemonGB,
                    color = BlackTextColor,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )


                if (state.downloadInfoType == DownloadType.NewInfo && state.downloadNewProperties.isNotEmpty()) {
                    Column {
                        Text(
                            text = stringResource(id = R.string.animated_enter_new_info_title_properties),
                            style = PokemonGB,
                            color = BlackTextColor,
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 2.dp)
                        )

                        state.downloadNewProperties.forEach {
                            Text(
                                text = " - " + stringResource(id = it),
                                style = PokemonGB,
                                color = BlackTextColor,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp)
                            )
                        }
                    }
                }

                Text(
                    text = stringResource(id = R.string.animated_enter_message_download_now),
                    style = PokemonGB,
                    color = BlackTextColor,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .background(MainRed, RoundedCornerShape(4.dp))
                        .border(1.dp, MainBlack, RoundedCornerShape(4.dp))
                        .padding(6.dp)
                        .clickable {
                            state.onDownloadClick()
                        }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_alert),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MainBlack),
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .size(10.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.animated_enter_message_alert),
                        style = PokemonGB,
                        color = BlackTextColor,
                        fontSize = 8.sp
                    )
                }
            }
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
                    formattedDownloadProgress = "75%",
                    showDownloadMessage = true,
                    downloadInfoType = DownloadType.NewInfo,
                    downloadNewProperties = listOf(
                        UpdateInfo.Height.getDescription(),
                        UpdateInfo.Weight.getDescription()
                    )
                )
            )
        }
    }
}