package com.project.mypokedex.ui.scaffold

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import com.project.mypokedex.model.MainBottomAppBarComponent
import com.project.mypokedex.model.MainDrawerMenuComponent
import com.project.mypokedex.model.MainDrawerMenuComponent.changeDrawerState
import com.project.mypokedex.model.MainTopAppBarComponent
import com.project.mypokedex.ui.scaffold.components.BottomBar
import com.project.mypokedex.ui.scaffold.components.DrawerContent
import com.project.mypokedex.ui.scaffold.components.TopBar
import com.project.mypokedex.ui.stateholders.BottomAppBarUIState
import com.project.mypokedex.ui.stateholders.DrawerMenuUIState
import com.project.mypokedex.ui.stateholders.TopAppBarUIState
import com.project.mypokedex.ui.theme.MyPokedexTheme
import kotlinx.coroutines.launch

@Composable
fun MainScaffold(
    topAppBarState: TopAppBarUIState = MainTopAppBarComponent.topAppBarState(),
    bottomAppBarState: BottomAppBarUIState = MainBottomAppBarComponent.bottomAppBarState(),
    drawerMenuState: DrawerMenuUIState = MainDrawerMenuComponent.drawerMenuState(),
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = rememberDrawerState(drawerMenuState.drawerValue, confirmStateChange = {
        changeDrawerState(it)
        true
    })

    state.apply {
        scope.launch {
            if (drawerMenuState.drawerValue == DrawerValue.Closed) {
                close()
            } else {
                open()
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = state,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(300.dp)
            ) {
                DrawerContent(drawerMenuState)
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    state = topAppBarState,
                    onMenuClick = {
                        scope.launch {
                            changeDrawerState()
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