package com.project.mypokedex.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import androidx.navigation.navOptions
import com.project.mypokedex.BottomAppBarItem
import com.project.mypokedex.TopAppBarStateHolder

internal const val homeGraphRoute = "home"

fun NavGraphBuilder.homeGraph(onNewRoute: (TopAppBarStateHolder) -> Unit = {}) {
    navigation(
        startDestination = gridRoute,
        route = homeGraphRoute
    ) {
        gridScreen(onNewRoute)
        listScreen(onNewRoute)
    }
}

fun NavController.navigateToHomeGraph() {
    navigate(homeGraphRoute)
}

fun NavController.navigateSingleTopWithPopUpTo(bottomAppBarItem: BottomAppBarItem) {
    val (route, navigate) = when (bottomAppBarItem) {
        BottomAppBarItem.GridScreen -> Pair(
            gridRoute,
            ::navigateToGrid
        )

        BottomAppBarItem.ListScreen -> Pair(
            listRoute,
            ::navigateToList
        )
    }

    val navOptions = navOptions {
        launchSingleTop = true
        popUpTo(route)
    }

    navigate(navOptions)
}