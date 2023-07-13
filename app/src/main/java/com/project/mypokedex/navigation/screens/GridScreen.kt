package com.project.mypokedex.navigation.screens

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
import com.project.mypokedex.model.TopAppBarActionItem
import com.project.mypokedex.ui.screens.GridUIScreen
import com.project.mypokedex.ui.viewmodels.GridScreenViewModel

object GridScreen : Screen {

    private lateinit var viewModel: GridScreenViewModel
    private const val gridRoute = "GridScreen"

    override val topAppBarComponent: TopAppBarComponent = object : TopAppBarComponent {
        override fun getTitle(): Int = R.string.grid_screen_title

        override fun hasReturn(): Boolean = false

        override fun getActionItems(): List<TopAppBarActionItem> = listOf(
            TopAppBarActionItem(
                icon = R.drawable.ic_search,
                onClick = {
                    viewModel.onSearchClick()
                }
            )
        )
    }

    override val bottomAppBarComponent: BottomAppBarComponent = object : BottomAppBarComponent {
        override fun getItems(): List<BottomAppBarItem> {
            return BottomAppBarItem.HomeBottomAppBarItems
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