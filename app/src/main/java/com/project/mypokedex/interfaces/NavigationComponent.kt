package com.project.mypokedex.interfaces

import androidx.navigation.NavGraphBuilder
import com.project.mypokedex.model.Pokemon

interface NavigationComponent : BasicNavigation {

    fun NavGraphBuilder.screen(
        onClickPokemon: (Pokemon) -> Unit = {}
    )
}