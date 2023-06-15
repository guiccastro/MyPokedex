package com.project.mypokedex.navigation

sealed class NavigationRoute(val route: String) {
    object GridScreen: NavigationRoute("GridScreen")
    object SimpleScreen: NavigationRoute("SimpleScreen")
}