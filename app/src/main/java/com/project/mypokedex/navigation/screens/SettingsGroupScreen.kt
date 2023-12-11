package com.project.mypokedex.navigation.screens

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.navigation
import com.project.mypokedex.interfaces.GroupNavigation
import com.project.mypokedex.interfaces.Screen

object SettingsGroupScreen : GroupNavigation {

    private const val settingsGraphRoute = "settings"

    override val listScreens: List<Screen> = listOf(
        SettingsScreen
    )

    override fun NavGraphBuilder.graph() {
        navigation(
            startDestination = SettingsScreen.getRoute(),
            route = settingsGraphRoute
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

    override fun getRoute(): String = settingsGraphRoute

    override fun NavController.navigateToItself(pokemonId: Int?, navOptions: NavOptions?) =
        navigate(settingsGraphRoute)
}