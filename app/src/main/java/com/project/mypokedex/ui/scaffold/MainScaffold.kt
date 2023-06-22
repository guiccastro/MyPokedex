package com.project.mypokedex.ui.scaffold

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.project.mypokedex.model.BottomAppBarItem
import com.project.mypokedex.ui.stateholders.TopAppBarStateHolder
import com.project.mypokedex.model.bottomAppBarItems
import com.project.mypokedex.ui.components.Background
import com.project.mypokedex.ui.scaffold.components.BottomBar
import com.project.mypokedex.ui.scaffold.components.TopBar
import com.project.mypokedex.ui.screens.AnimatedEnter
import com.project.mypokedex.ui.stateholders.AnimatedEnterStateHolder
import com.project.mypokedex.ui.theme.MyPokedexTheme

@Composable
fun MainScaffold(
    animatedEnterState: AnimatedEnterStateHolder = AnimatedEnterStateHolder(),
    topAppBarState: TopAppBarStateHolder = TopAppBarStateHolder(),
    onNavigateBottomBar: (BottomAppBarItem) -> Unit = {},
    content: @Composable () -> Unit
) {
    Background()

    Scaffold(
        topBar = { TopBar(state = topAppBarState) },
        bottomBar = { BottomBar(bottomAppBarItems, onNavigateBottomBar) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            content()
        }
    }

    AnimatedEnter(state = animatedEnterState)
}

@Preview
@Composable
fun MyPokedexAppPreview() {
    MyPokedexTheme {
        Surface {
            MainScaffold(AnimatedEnterStateHolder(isDownloading = false)) {

            }
        }
    }
}