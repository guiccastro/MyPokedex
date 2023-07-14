package com.project.mypokedex.ui.scaffold.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.mypokedex.model.MainBottomAppBarComponent.onClickBottomAppBarItem
import com.project.mypokedex.navigation.screens.DetailsScreen
import com.project.mypokedex.ui.components.customShadow
import com.project.mypokedex.ui.components.topBorder
import com.project.mypokedex.ui.stateholders.BottomAppBarUIState
import com.project.mypokedex.ui.theme.Black
import com.project.mypokedex.ui.theme.BottomAppBarSelectedItem
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MyPokedexTheme

@Composable
fun BottomBar(
    state: BottomAppBarUIState = BottomAppBarUIState(),
) {
    val hasBottomAppBar = state.bottomAppBarComponent != null
    val items = state.bottomAppBarComponent?.getItems() ?: emptyList()
    AnimatedVisibility(
        visible = hasBottomAppBar,
        enter = slideInVertically(
            animationSpec = tween(1000),
            initialOffsetY = { it },
        ),
        exit = slideOutVertically(
            animationSpec = tween(1000),
            targetOffsetY = { it },
        )
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
                            Image(
                                imageVector = item.icon,
                                contentDescription = stringResource(item.label),
                                colorFilter = ColorFilter.tint(MainBlack)
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(item.label),
                                fontWeight = FontWeight(500),
                                color = BlackTextColor,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
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

@Preview(showSystemUi = true)
@Composable
fun BottomAppBarPreview() {
    MyPokedexTheme {
        Surface {
            Scaffold(
                bottomBar = {
                    BottomBar(state = BottomAppBarUIState(bottomAppBarComponent = DetailsScreen.bottomAppBarComponent))
                }
            ) {
                Surface(modifier = Modifier.padding(it)) {

                }
            }
        }
    }
}