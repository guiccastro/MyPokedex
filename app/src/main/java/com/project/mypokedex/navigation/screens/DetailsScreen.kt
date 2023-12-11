package com.project.mypokedex.navigation.screens

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.mypokedex.R
import com.project.mypokedex.interfaces.BottomAppBarComponent
import com.project.mypokedex.interfaces.Screen
import com.project.mypokedex.interfaces.TopAppBarComponent
import com.project.mypokedex.model.TopAppBarActionItem
import com.project.mypokedex.ui.screens.DetailsUIScreen
import com.project.mypokedex.ui.viewmodels.DetailsScreenViewModel

object DetailsScreen : Screen {

    private const val detailsRoute = "DetailsScreen"
    const val pokemonIdArgument = "pokemonId"

    override val topAppBarComponent: TopAppBarComponent = object : TopAppBarComponent {
        override fun getTitle(): Int = R.string.details_screen_title

        override fun hasMenu(): Boolean = false

        override fun hasReturn(): Boolean = true

        override fun getActionItems(): List<TopAppBarActionItem> = emptyList()
    }

    override val bottomAppBarComponent: BottomAppBarComponent? = null

    override fun NavGraphBuilder.screen() {
        composable(
            route = "$detailsRoute/{$pokemonIdArgument}",
            arguments = listOf(navArgument(pokemonIdArgument) { type = NavType.IntType })
        ) {
            val viewModel: DetailsScreenViewModel = hiltViewModel()
            val state = viewModel.uiState.collectAsState().value
            DetailsUIScreen(state = state)
        }
    }

    override fun getRoute(): String = detailsRoute

    override fun NavController.navigateToItself(pokemonId: Int?, navOptions: NavOptions?) =
        navigate("$detailsRoute/$pokemonId", navOptions)
}