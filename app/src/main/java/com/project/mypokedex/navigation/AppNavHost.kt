package com.project.mypokedex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.navigation.destinations.detailsScreen
import com.project.mypokedex.navigation.destinations.homeGraph
import com.project.mypokedex.navigation.destinations.homeGraphRoute
import com.project.mypokedex.ui.stateholders.TopAppBarStateHolder

@Composable
fun AppNavHost(
    navController: NavHostController,
    onNewRoute: (TopAppBarStateHolder) -> Unit = {},
    onClickPokemon: (Pokemon) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = homeGraphRoute
    ) {
        homeGraph(onNewRoute = onNewRoute, onClickPokemon = onClickPokemon)
        detailsScreen()
    }
}