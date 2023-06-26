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
import com.project.mypokedex.navigation.destinations.navigateSingleTopWithPopUpTo
import com.project.mypokedex.navigation.destinations.navigateToDetailsScreen
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
                        BottomAppBarItem.GridScreen
                    )
                }
                MainScaffold(
                    animatedEnterState = animatedEnterViewModel.animatedEnterStateHolder.collectAsState().value,
                    topAppBarState = topAppBarState,
                    bottomAppBarSelectedItem = bottomAppBarSelectedItem,
                    bottomAppBarItems = bottomAppBarItems,
                    onClickBottomAppBarItem = { bottomAppBarItem ->
                        bottomAppBarSelectedItem = bottomAppBarItem
                        navController.navigateSingleTopWithPopUpTo(bottomAppBarItem)
                    }
                ) {
                    AppNavHost(
                        navController = navController,
                        onNewRoute = { newTopAppBarState ->
                            topAppBarState = newTopAppBarState
                        },
                        onClickPokemon = { pokemon ->
                            navController.navigateToDetailsScreen(pokemonId = pokemon.id)
                        }
                    )
                }
            }
        }
    }
}


