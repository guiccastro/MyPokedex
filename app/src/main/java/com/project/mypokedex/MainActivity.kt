package com.project.mypokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.project.mypokedex.model.BottomAppBarItem
import com.project.mypokedex.model.bottomAppBarItems
import com.project.mypokedex.navigation.AppNavHost
import com.project.mypokedex.navigation.destinations.DetailsScreen
import com.project.mypokedex.navigation.getSingleTopWithPopUpTo
import com.project.mypokedex.ui.scaffold.MainScaffold
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
                var bottomAppBarSelectedItem by remember {
                    mutableStateOf<BottomAppBarItem>(
                        BottomAppBarItem.GridScreenBottomAppBar
                    )
                }
                MainScaffold(
                    animatedEnterState = animatedEnterViewModel.animatedEnterStateHolder.collectAsState().value,
                    topAppBarState = topAppBarState,
                    bottomAppBarSelectedItem = bottomAppBarSelectedItem,
                    bottomAppBarItems = bottomAppBarItems,
                    onClickBottomAppBarItem = { bottomAppBarItem ->
                        bottomAppBarSelectedItem = bottomAppBarItem
                        val topAppBarComponent = bottomAppBarItem.screen.topAppBarComponent
                        topAppBarState = TopAppBarStateHolder(
                            itemsList = topAppBarComponent?.getItems() ?: emptyList(),
                            title = topAppBarComponent?.getTitle()
                        )
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
                            topAppBarState = TopAppBarStateHolder(
                                itemsList = DetailsScreen.topAppBarComponent.getItems(),
                                title = DetailsScreen.topAppBarComponent.getTitle()
                            )

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


