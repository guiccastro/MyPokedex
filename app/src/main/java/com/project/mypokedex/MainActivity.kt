package com.project.mypokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.project.mypokedex.model.bottomAppBarItems
import com.project.mypokedex.navigation.AppNavHost
import com.project.mypokedex.navigation.destinations.DetailsScreen
import com.project.mypokedex.navigation.getScreen
import com.project.mypokedex.navigation.getSingleTopWithPopUpTo
import com.project.mypokedex.ui.scaffold.MainScaffold
import com.project.mypokedex.ui.stateholders.BottomAppBarStateHolder
import com.project.mypokedex.ui.stateholders.TopAppBarStateHolder
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.viewmodels.AnimatedEnterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPokedexTheme {
                val animatedEnterViewModel: AnimatedEnterViewModel by viewModels()
                val navController = rememberNavController()
                var topAppBarState by remember { mutableStateOf(TopAppBarStateHolder()) }
                var bottomAppBarState by remember { mutableStateOf(BottomAppBarStateHolder()) }
                LaunchedEffect(Unit) {
                    navController.addOnDestinationChangedListener { _, destination, _ ->
                        val route = destination.route?.split("/")?.first() ?: ""
                        val screen = getScreen(route)
                        val bottomAppBarItemSelected =
                            bottomAppBarItems.find { it.screen == screen }

                        topAppBarState = TopAppBarStateHolder(
                            itemsList = screen?.topAppBarComponent?.getItems() ?: emptyList(),
                            title = screen?.topAppBarComponent?.getTitle()
                        )
                        bottomAppBarState = BottomAppBarStateHolder(
                            selectedItem = bottomAppBarItemSelected,
                            bottomAppBarComponent = screen?.bottomAppBarComponent
                        )
                    }
                }

                MainScaffold(
                    animatedEnterState = animatedEnterViewModel.animatedEnterStateHolder.collectAsState().value,
                    topAppBarState = topAppBarState,
                    bottomAppBarState = bottomAppBarState,
                    onClickBottomAppBarItem = { bottomAppBarItem ->
                        navController.apply {
                            bottomAppBarItems.find { it == bottomAppBarItem }?.screen?.apply {
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


