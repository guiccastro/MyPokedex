package com.project.mypokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.project.mypokedex.model.BottomAppBarItem
import com.project.mypokedex.navigation.AppNavHost
import com.project.mypokedex.navigation.getScreen
import com.project.mypokedex.navigation.getSingleTopWithPopUpTo
import com.project.mypokedex.navigation.screens.DetailsScreen
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
                val navController = rememberNavController()
                var topAppBarState by remember { mutableStateOf(TopAppBarUIState()) }
                var bottomAppBarState by remember { mutableStateOf(BottomAppBarUIState()) }
                LaunchedEffect(Unit) {
                    navController.addOnDestinationChangedListener { _, destination, _ ->
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
                }

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
                        navController = navController,
                        onClickPokemon = { pokemon ->
                            navController.apply {
                                DetailsScreen.apply {
                                    navigateToItself(pokemonId = pokemon.id)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}


