package com.project.mypokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.mypokedex.model.BottomAppBarItem
import com.project.mypokedex.navigation.MainNavComponent.Companion.AppNavHost
import com.project.mypokedex.navigation.MainNavComponent.Companion.getScreen
import com.project.mypokedex.navigation.MainNavComponent.Companion.getSingleTopWithPopUpTo
import com.project.mypokedex.navigation.MainNavComponent.Companion.navController
import com.project.mypokedex.ui.scaffold.MainScaffold
import com.project.mypokedex.ui.stateholders.BottomAppBarUIState
import com.project.mypokedex.ui.stateholders.TopAppBarUIState
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.viewmodels.AnimatedEnterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPokedexTheme {
                val animatedEnterViewModel: AnimatedEnterViewModel = hiltViewModel()
                var topAppBarState by remember { mutableStateOf(TopAppBarUIState()) }
                var bottomAppBarState by remember { mutableStateOf(BottomAppBarUIState()) }


                MainScaffold(
                    animatedEnterState = animatedEnterViewModel.animatedEnterUIState.collectAsState().value,
                    topAppBarState = topAppBarState,
                    bottomAppBarState = bottomAppBarState,
                    onClickBottomAppBarItem = { bottomAppBarItem ->
                        navController.apply {
                            bottomAppBarItem.screen.apply {
                                navigateToItself(navOptions = getSingleTopWithPopUpTo(getRoute()))
                            }
                        }
                    }
                ) {
                    AppNavHost(
                        onDestinationChanged = { destination ->
                            val route = destination.route?.split("/")?.first() ?: ""
                            val screen = getScreen(route)
                            val bottomAppBarItemSelected =
                                BottomAppBarItem.findByScreen(screen)

                            screen?.topAppBarComponent?.let { topAppBarComponent ->
                                topAppBarState = TopAppBarUIState(
                                    title = topAppBarComponent.getTitle(),
                                    hasReturn = topAppBarComponent.hasReturn(),
                                    onClickReturn = { navController.popBackStack() },
                                    actionItems = topAppBarComponent.getActionItems()
                                )
                            }
                            bottomAppBarState = BottomAppBarUIState(
                                selectedItem = bottomAppBarItemSelected,
                                bottomAppBarComponent = screen?.bottomAppBarComponent
                            )
                        }
                    )
                }
            }
        }
    }
}


