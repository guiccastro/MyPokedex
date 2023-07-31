package com.project.mypokedex.interfaces

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions

interface GroupNavigation : BasicNavigation {

    val listScreens: List<Screen>

    fun NavGraphBuilder.graph()

    fun NavController.navigateTo(route: String, navOptions: NavOptions?)
}