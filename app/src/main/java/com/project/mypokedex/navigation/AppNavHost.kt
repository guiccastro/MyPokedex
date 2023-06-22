package com.project.mypokedex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.project.mypokedex.ui.stateholders.TopAppBarStateHolder
import com.project.mypokedex.navigation.destinations.homeGraph
import com.project.mypokedex.navigation.destinations.homeGraphRoute

@Composable
fun AppNavHost(
    navController: NavHostController,
    onNewRoute: (TopAppBarStateHolder) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = homeGraphRoute
    ) {
        homeGraph(onNewRoute = onNewRoute)
        // detailedScreen()
    }
}