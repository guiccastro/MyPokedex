package com.project.mypokedex.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.mypokedex.TopAppBarStateHolder
import com.project.mypokedex.ui.screens.SinglePokemonScreen
import com.project.mypokedex.ui.viewmodels.SinglePokemonScreenViewModel

fun NavGraphBuilder.simpleScreen(onNewRoute: (TopAppBarStateHolder) -> Unit = {}) {
    composable(AppDestination.SimpleScreen.route) {
        val viewModel: SinglePokemonScreenViewModel = hiltViewModel()
        onNewRoute(TopAppBarStateHolder())
        SinglePokemonScreen(viewModel = viewModel)
    }
}