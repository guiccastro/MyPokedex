package com.project.mypokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.mypokedex.navigation.NavigationRoute
import com.project.mypokedex.ui.components.basic.MyPokedexApp
import com.project.mypokedex.ui.screens.GridPokemonScreen
import com.project.mypokedex.ui.screens.SinglePokemonScreen
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.viewmodels.GridPokemonScreenViewModel
import com.project.mypokedex.ui.viewmodels.SinglePokemonScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPokedexTheme {
                val singleScreenViewModel: SinglePokemonScreenViewModel by viewModels()
                val gridScreenViewModel: GridPokemonScreenViewModel by viewModels()
                val navController = rememberNavController()
                MyPokedexApp(
                    state = gridScreenViewModel.uiState.collectAsState().value,
                    onNavigateBottomBar = { navigationRoute ->
                        navController.navigate(navigationRoute.route) {
                            launchSingleTop = true
                            popUpTo(navigationRoute.route)
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NavigationRoute.GridScreen.route
                    ) {
                        composable(NavigationRoute.GridScreen.route) {
                            GridPokemonScreen(viewModel = gridScreenViewModel)
                        }

                        composable(NavigationRoute.SimpleScreen.route) {
                            SinglePokemonScreen(viewModel = singleScreenViewModel)
                        }
                    }
                }
            }
        }
    }
}


