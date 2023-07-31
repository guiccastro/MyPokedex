package com.project.mypokedex.navigation.screens

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.navigation
import com.project.mypokedex.interfaces.GroupNavigation
import com.project.mypokedex.interfaces.Screen

object HomeGroupScreen : GroupNavigation {

    private const val homeGraphRoute = "home"

    override val listScreens: List<Screen>
        get() = listOf(
            GridScreen,
            ListScreen,
            GameScreen
        )

    override fun NavGraphBuilder.graph() {
        navigation(
            startDestination = GridScreen.getRoute(),
            route = homeGraphRoute
        ) {
            listScreens.forEach {
                it.apply {
                    screen()
                }
            }
        }
    }

    override fun NavController.navigateTo(route: String, navOptions: NavOptions?) {
        listScreens.find {
            it.getRoute() == route
        }?.let {
            it.apply {
                navigateToItself(navOptions = navOptions)
            }
        }
    }

    override fun getRoute(): String = homeGraphRoute

    override fun NavController.navigateToItself(pokemonId: Int?, navOptions: NavOptions?) =
        navigate(homeGraphRoute)
}