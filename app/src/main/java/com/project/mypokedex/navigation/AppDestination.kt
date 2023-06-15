package com.project.mypokedex.navigation

sealed class AppDestination(val route: String) {
    object GridScreen : AppDestination("GridScreen")
    object SimpleScreen : AppDestination("SimpleScreen")
}