package com.project.mypokedex.navigation.destinations

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.project.mypokedex.R
import com.project.mypokedex.interfaces.BottomAppBarComponent
import com.project.mypokedex.interfaces.Screen
import com.project.mypokedex.interfaces.TopAppBarComponent
import com.project.mypokedex.model.BottomAppBarItem
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.TopAppBarItem
import com.project.mypokedex.ui.screens.GridUIScreen
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.viewmodels.GridScreenViewModel

object GridScreen : Screen {

    private lateinit var viewModel: GridScreenViewModel
    private const val gridRoute = "GridScreen"

    override val topAppBarComponent: TopAppBarComponent = object : TopAppBarComponent {
        override fun getTitle(): String = "Grid Screen"

        override fun hasReturn(): Boolean = false

        override fun getItems(): List<TopAppBarItem> = listOf(
            TopAppBarItem(
                icon = R.drawable.ic_search,
                iconColor = BorderBlack,
                onClickEvent = {
                    viewModel.onSearchClick()
                }
            )
        )
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
        composable(gridRoute) {
            viewModel = hiltViewModel()
            GridUIScreen(viewModel = viewModel, onClick = onClickPokemon)
        }
    }

    override fun getRoute(): String = gridRoute

    override fun NavController.navigateToItself(pokemonId: Int?, navOptions: NavOptions?) =
        navigate(gridRoute, navOptions)
}