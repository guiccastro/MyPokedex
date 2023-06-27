package com.project.mypokedex.interfaces

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.project.mypokedex.model.Pokemon

interface GroupNavigation : BasicNavigation {

    val listScreens: List<Screen>

    fun NavGraphBuilder.graph(
        onClickPokemon: (Pokemon) -> Unit = {}
    )

    fun NavController.navigateTo(route: String, navOptions: NavOptions?)
}