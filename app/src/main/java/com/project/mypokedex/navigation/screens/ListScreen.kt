package com.project.mypokedex.navigation.screens

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
import com.project.mypokedex.model.TopAppBarActionItem
import com.project.mypokedex.ui.screens.ListUIScreen
import com.project.mypokedex.ui.viewmodels.ListScreenViewModel

object ListScreen : Screen {

    private const val listRoute = "ListScreen"

    override val topAppBarComponent: TopAppBarComponent = object : TopAppBarComponent {
        override fun getTitle(): String = "List Screen"

        override fun hasReturn(): Boolean = false

        override fun getActionItems(): List<TopAppBarActionItem> = emptyList()
    }

    override val bottomAppBarComponent: BottomAppBarComponent = object : BottomAppBarComponent {
        override fun getItems(): List<BottomAppBarItem> {
            return listOf(
                BottomAppBarItem.GridScreenBottomAppBarItem,
                BottomAppBarItem.ListScreenBottomAppBarItem
            )
        }
    }

    override fun NavGraphBuilder.screen(
        onClickPokemon: (Pokemon) -> Unit
    ) {
        composable(listRoute) {
            val viewModel: ListScreenViewModel = hiltViewModel()
            ListUIScreen(viewModel = viewModel)
        }
    }

    override fun getRoute(): String = listRoute

    override fun NavController.navigateToItself(pokemonId: Int?, navOptions: NavOptions?) =
        navigate(listRoute, navOptions)


}