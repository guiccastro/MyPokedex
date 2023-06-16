package com.project.mypokedex.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.mypokedex.TopAppBarStateHolder
import com.project.mypokedex.ui.screens.GridPokemonScreen
import com.project.mypokedex.ui.viewmodels.GridPokemonScreenViewModel

fun NavGraphBuilder.gridScreen(onNewRoute: (TopAppBarStateHolder) -> Unit = {}) {
    composable(AppDestination.GridScreen.route) {
        val viewModel: GridPokemonScreenViewModel = hiltViewModel()
        onNewRoute(viewModel.topBarState.collectAsState().value)
        GridPokemonScreen(viewModel = viewModel)
    }
}