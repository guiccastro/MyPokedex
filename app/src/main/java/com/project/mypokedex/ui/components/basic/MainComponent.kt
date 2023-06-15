package com.project.mypokedex.ui.components.basic

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.project.mypokedex.sampledata.bottomAppItemsSample
import com.project.mypokedex.ui.components.Background
import com.project.mypokedex.ui.screens.AnimatedEnter
import com.project.mypokedex.ui.stateholders.GridPokemonScreenStateHolder
import com.project.mypokedex.ui.theme.MyPokedexTheme

@Composable
fun MyPokedexApp(
    state: GridPokemonScreenStateHolder = GridPokemonScreenStateHolder(isDownloading = false),
    content: @Composable () -> Unit
) {
    Background()

    Scaffold(
        topBar = { TopBar(state = state) },
        bottomBar = { BottomBar(bottomAppItemsSample) }
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
            MyPokedexApp {

            }
        }
    }
}