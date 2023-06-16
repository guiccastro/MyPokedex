package com.project.mypokedex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.project.mypokedex.TopAppBarStateHolder

@Composable
fun MyPokedexNavHost(
    navController: NavHostController,
    onNewRoute: (TopAppBarStateHolder) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.GridScreen.route
    ) {
        gridScreen(onNewRoute)
        simpleScreen(onNewRoute)
    }
}