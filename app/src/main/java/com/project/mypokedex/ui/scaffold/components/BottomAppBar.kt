package com.project.mypokedex.ui.scaffold.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.extensions.customShadow
import com.project.mypokedex.extensions.topBorder
import com.project.mypokedex.model.MainBottomAppBarComponent.onClickBottomAppBarItem
import com.project.mypokedex.navigation.screens.GridScreen
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.components.AppIcon
import com.project.mypokedex.ui.stateholders.BottomAppBarUIState
import com.project.mypokedex.ui.theme.Black
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.BottomAppBarSelectedItem
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainGreen
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import kotlinx.coroutines.launch

@Composable
fun BottomBar(
    state: BottomAppBarUIState = BottomAppBarUIState(),
) {
    Column {

        val downloadProgress = remember { mutableFloatStateOf(0F) }
        val scope = rememberCoroutineScope()

        LaunchedEffect(key1 = Unit) {
            scope.launch {
                PokemonRepository.downloaderInfo.progressRequest.collect {
                    downloadProgress.floatValue = it
                }
            }
        }

        AnimatedVisibility(
            visible = downloadProgress.floatValue < 1F,
            exit = shrinkVertically()
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.download_description),
                    fontSize = 8.sp,
                    color = BlackTextColor,
                    style = PokemonGB,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 0.dp)
                )

                LinearProgressIndicator(
                    progress = downloadProgress.floatValue,
                    modifier = Modifier
                        .height(4.dp)
                        .fillMaxWidth()
                        .topBorder((0.5).dp, MainBlack),
                    color = MainGreen
                )
            }
        }

        val hasBottomAppBar = state.bottomAppBarComponent != null
        val items = state.bottomAppBarComponent?.getItems() ?: emptyList()
        AnimatedVisibility(
            visible = hasBottomAppBar,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            NavigationBar(
                modifier = Modifier
                    .topBorder(1.dp, MainBlack)
                    .customShadow(
                        color = Black.copy(alpha = 0.7f),
                        blurRadius = 5.dp,
                        widthOffset = 10.dp
                    )
            ) {
                if (items.isNotEmpty()) {
                    items.forEach { item ->
                        NavigationBarItem(
                            selected = state.selectedItem == item,
                            onClick = {
                                onClickBottomAppBarItem(item)
                            },
                            icon = {
                                AppIcon(
                                    id = item.icon
                                )
                            },
                            label = {
                                Text(
                                    text = stringResource(item.label).uppercase(),
                                    fontWeight = FontWeight(1000),
                                    color = BlackTextColor,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = PokemonGB,
                                    fontSize = 8.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = BottomAppBarSelectedItem
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun BottomAppBarPreview() {
    MyPokedexTheme {
        Surface {
            Scaffold(
                bottomBar = {
                    BottomBar(state = BottomAppBarUIState(bottomAppBarComponent = GridScreen.bottomAppBarComponent))
                }
            ) {
                Surface(modifier = Modifier.padding(it)) {

                }
            }
        }
    }
}