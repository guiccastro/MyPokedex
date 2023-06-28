package com.project.mypokedex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.project.mypokedex.interfaces.GroupNavigation
import com.project.mypokedex.interfaces.Screen
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.navigation.screens.DetailsScreen
import com.project.mypokedex.navigation.screens.HomeGroupScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    onClickPokemon: (Pokemon) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = HomeGroupScreen.getRoute()
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

val screensList = listOf<Screen>(
    DetailsScreen
)

val groupNavigation = listOf<GroupNavigation>(
    HomeGroupScreen
)

fun getAllScreens(): List<Screen> {
    val screens = ArrayList<Screen>()
    screens.addAll(screensList)
    groupNavigation.forEach { screens.addAll(it.listScreens) }
    return screens
}

fun getScreen(route: String): Screen? {
    return getAllScreens().find {
        it.getRoute() == route
    }
}

fun getSingleTopWithPopUpTo(route: String): NavOptions {
    return navOptions {
        launchSingleTop = true
        popUpTo(route)
    }
}