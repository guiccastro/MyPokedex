package com.project.mypokedex.navigation.destinations

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.project.mypokedex.interfaces.BottomAppBarComponent
import com.project.mypokedex.interfaces.Screen
import com.project.mypokedex.interfaces.TopAppBarComponent
import com.project.mypokedex.model.BottomAppBarItem
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.TopAppBarItem
import com.project.mypokedex.ui.screens.ListPokemonScreen
import com.project.mypokedex.ui.viewmodels.ListPokemonScreenViewModel

internal const val listRoute = "ListScreen"

object ListScreen : Screen {
    override val topAppBarComponent: TopAppBarComponent = object : TopAppBarComponent {
        override fun getTitle(): String = "List Screen"

        override fun hasReturn(): Boolean = false

        override fun getItems(): List<TopAppBarItem> = emptyList()
    }

    override val bottomAppBarComponent: BottomAppBarComponent = object : BottomAppBarComponent {
        override fun getItems(): List<BottomAppBarItem> {
            return listOf(
                BottomAppBarItem.GridScreenBottomAppBar,
                BottomAppBarItem.ListScreenBottomAppBar
            )
        }
    }

    override fun NavGraphBuilder.screen(
        onClickPokemon: (Pokemon) -> Unit
    ) {
        composable(listRoute) {
            val viewModel: ListPokemonScreenViewModel = hiltViewModel()
            ListPokemonScreen(viewModel = viewModel)
        }
    }

    override fun getRoute(): String = listRoute

    override fun NavController.navigateToItself(pokemonId: Int?, navOptions: NavOptions?) =
        navigate(listRoute, navOptions)


}