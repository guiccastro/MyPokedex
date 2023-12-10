package com.project.mypokedex.ui.scaffold

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.project.mypokedex.model.MainBottomAppBarComponent
import com.project.mypokedex.model.MainTopAppBarComponent
import com.project.mypokedex.ui.scaffold.components.BottomBar
import com.project.mypokedex.ui.scaffold.components.TopBar
import com.project.mypokedex.ui.stateholders.BottomAppBarUIState
import com.project.mypokedex.ui.stateholders.TopAppBarUIState
import com.project.mypokedex.ui.theme.MyPokedexTheme
import kotlinx.coroutines.launch

@Composable
fun MainScaffold(
    topAppBarState: TopAppBarUIState = MainTopAppBarComponent.topAppBarState(),
    bottomAppBarState: BottomAppBarUIState = MainBottomAppBarComponent.bottomAppBarState(),
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet { /* Drawer content */ }
        },
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    state = topAppBarState,
                    onMenuClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            },
            bottomBar = { BottomBar(state = bottomAppBarState) }
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun MyPokedexAppPreview() {
    MyPokedexTheme {
        Surface {
            MainScaffold {
            }
        }
    }
}