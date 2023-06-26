package com.project.mypokedex.navigation.destinations

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.mypokedex.ui.screens.DetailsScreen
import com.project.mypokedex.ui.viewmodels.DetailsScreenViewModel

private const val detailsRoute = "DetailsScreen"
internal const val pokemonIdArgument = "pokemonId"

fun NavGraphBuilder.detailsScreen() {
    composable(
        route = "$detailsRoute/{$pokemonIdArgument}",
        arguments = listOf(navArgument(pokemonIdArgument) { type = NavType.IntType })
    ) {
        val viewModel: DetailsScreenViewModel = hiltViewModel()
        val state = viewModel.uiState.collectAsState().value
        DetailsScreen(state = state)
    }
}

fun NavController.navigateToDetailsScreen(pokemonId: Int, navOptions: NavOptions? = null) {
    navigate("$detailsRoute/$pokemonId", navOptions)
}