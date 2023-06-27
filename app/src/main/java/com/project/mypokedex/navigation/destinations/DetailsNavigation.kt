package com.project.mypokedex.navigation.destinations

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.mypokedex.interfaces.BottomAppBarComponent
import com.project.mypokedex.interfaces.Screen
import com.project.mypokedex.interfaces.TopAppBarComponent
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.TopAppBarItem
import com.project.mypokedex.ui.screens.DetailsScreen
import com.project.mypokedex.ui.viewmodels.DetailsScreenViewModel

private const val detailsRoute = "DetailsScreen"
internal const val pokemonIdArgument = "pokemonId"

object DetailsScreen : Screen {
    override val topAppBarComponent: TopAppBarComponent = object : TopAppBarComponent {
        override fun getTitle(): String = "Details"

        override fun hasReturn(): Boolean = false

        override fun getItems(): List<TopAppBarItem> = emptyList()
    }

    override val bottomAppBarComponent: BottomAppBarComponent? = null

    override fun NavGraphBuilder.screen(
        onClickPokemon: (Pokemon) -> Unit
    ) {
        composable(
            route = "$detailsRoute/{$pokemonIdArgument}",
            arguments = listOf(navArgument(pokemonIdArgument) { type = NavType.IntType })
        ) {
            val viewModel: DetailsScreenViewModel = hiltViewModel()
            val state = viewModel.uiState.collectAsState().value
            DetailsScreen(state = state)
        }
    }

    override fun getRoute(): String = detailsRoute

    override fun NavController.navigateToItself(pokemonId: Int?, navOptions: NavOptions?) =
        navigate("$detailsRoute/$pokemonId", navOptions)
}