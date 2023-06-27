package com.project.mypokedex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.project.mypokedex.interfaces.GroupNavigation
import com.project.mypokedex.interfaces.Screen
import com.project.mypokedex.model.BottomAppBarItem
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.navigation.destinations.DetailsScreen
import com.project.mypokedex.navigation.destinations.HomeGroupScreen
import com.project.mypokedex.navigation.destinations.homeGraphRoute

@Composable
fun AppNavHost(
    navController: NavHostController,
    onClickPokemon: (Pokemon) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = homeGraphRoute
    ) {
        groupNavigation.forEach {
            it.apply {
                graph(onClickPokemon = onClickPokemon)
            }
        }
        screensList.forEach {
            it.apply {
                screen()
            }
        }
    }
}

val screensList = listOf(
    DetailsScreen
)

val groupNavigation = listOf<GroupNavigation>(
    HomeGroupScreen
)

fun getSingleTopWithPopUpTo(route: String): NavOptions {
    return navOptions {
        launchSingleTop = true
        popUpTo(route)
    }
}

fun NavHostController.findRoute(bottomItem: BottomAppBarItem) {
    val currentRoute = currentBackStack.value.last().destination.route?.split("/")?.first()

    var currentScreen: Screen? = null
    groupNavigation.forEach { group ->
        group.listScreens.find { screen ->
            screen.getRoute() == currentRoute
        }?.let {
            currentScreen = it
        }
    }

    currentScreen?.bottomAppBarComponent?.getItems()?.forEach {

    }
}