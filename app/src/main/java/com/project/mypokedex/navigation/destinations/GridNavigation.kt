package com.project.mypokedex.navigation.destinations

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.ui.screens.GridPokemonScreen
import com.project.mypokedex.ui.stateholders.TopAppBarStateHolder
import com.project.mypokedex.ui.viewmodels.GridPokemonScreenViewModel

internal const val gridRoute = "GridScreen"

fun NavGraphBuilder.gridScreen(
    onNewRoute: (TopAppBarStateHolder) -> Unit = {},
    onClickPokemon: (Pokemon) -> Unit = {}
) {
    composable(gridRoute) {
        val viewModel: GridPokemonScreenViewModel = hiltViewModel()
        onNewRoute(viewModel.topBarState.collectAsState().value)
        GridPokemonScreen(viewModel = viewModel, onClick = onClickPokemon)
    }
}

fun NavController.navigateToGridScreen(navOptions: NavOptions? = null) {
    navigate(gridRoute, navOptions)
}