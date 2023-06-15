package com.project.mypokedex.ui.components.basic

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.project.mypokedex.navigation.NavigationRoute
import com.project.mypokedex.ui.components.Background
import com.project.mypokedex.ui.screens.AnimatedEnter
import com.project.mypokedex.ui.stateholders.gridscreen.GridScreenStateHolder
import com.project.mypokedex.ui.theme.MyPokedexTheme

private val bottomAppBarItems = listOf(
    BottomAppBarItem.GridScreen,
    BottomAppBarItem.SimpleScreen,
)

@Composable
fun MyPokedexApp(
    state: GridScreenStateHolder = GridScreenStateHolder(),
    onNavigateBottomBar: (NavigationRoute) -> Unit = {},
    content: @Composable () -> Unit
) {
    Background()

    Scaffold(
        topBar = { TopBar(state = state) },
        bottomBar = { BottomBar(bottomAppBarItems, onNavigateBottomBar) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            content()
        }
    }

    AnimatedEnter(state = state)
}

@Preview
@Composable
fun MyPokedexAppPreview() {
    MyPokedexTheme {
        Surface {
            MyPokedexApp(GridScreenStateHolder(isDownloading = false)) {

            }
        }
    }
}