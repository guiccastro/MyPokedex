package com.project.mypokedex.navigation.destinations

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.project.mypokedex.ui.stateholders.TopAppBarStateHolder
import com.project.mypokedex.ui.screens.ListPokemonScreen
import com.project.mypokedex.ui.viewmodels.ListPokemonScreenViewModel

internal const val listRoute = "ListScreen"

fun NavGraphBuilder.listScreen(onNewRoute: (TopAppBarStateHolder) -> Unit = {}) {
    composable(listRoute) {
        val viewModel: ListPokemonScreenViewModel = hiltViewModel()
        onNewRoute(TopAppBarStateHolder())
        ListPokemonScreen(viewModel = viewModel)
    }
}

fun NavController.navigateToListScreen(navOptions: NavOptions? = null) {
    navigate(listRoute, navOptions)
}