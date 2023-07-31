package com.project.mypokedex.interfaces

import androidx.navigation.NavGraphBuilder

interface NavigationComponent : BasicNavigation {

    fun NavGraphBuilder.screen()
}