package com.project.mypokedex.navigation.screens

import androidx.compose.runtime.collectAsState
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
import com.project.mypokedex.ui.screens.GameUIScreen
import com.project.mypokedex.ui.viewmodels.GameScreenViewModel

object GameScreen : Screen {
    private const val gameRoute = "GameScreen"

    override val topAppBarComponent: TopAppBarComponent? = null
    override val bottomAppBarComponent: BottomAppBarComponent = object : BottomAppBarComponent {
        override fun getItems(): List<BottomAppBarItem> {
            return BottomAppBarItem.HomeBottomAppBarItems
        }
    }

    override fun NavGraphBuilder.screen(onClickPokemon: (Pokemon) -> Unit) {
        composable(gameRoute) {
            val viewModel: GameScreenViewModel = hiltViewModel()
            GameUIScreen(state = viewModel.uiState.collectAsState().value)
        }
    }

    override fun getRoute(): String = gameRoute

    override fun NavController.navigateToItself(pokemonId: Int?, navOptions: NavOptions?) =
        navigate(gameRoute, navOptions)
}