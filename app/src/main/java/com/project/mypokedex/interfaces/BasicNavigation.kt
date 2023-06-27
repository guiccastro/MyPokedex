package com.project.mypokedex.interfaces

import androidx.navigation.NavController
import androidx.navigation.NavOptions

interface BasicNavigation {

    fun getRoute(): String

    fun NavController.navigateToItself(
        pokemonId: Int? = null,
        navOptions: NavOptions? = null
    )
}