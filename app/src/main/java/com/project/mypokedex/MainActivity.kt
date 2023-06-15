package com.project.mypokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.mypokedex.navigation.AppDestination
import com.project.mypokedex.ui.components.basic.MyPokedexApp
import com.project.mypokedex.ui.screens.GridPokemonScreen
import com.project.mypokedex.ui.screens.SinglePokemonScreen
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.viewmodels.AnimatedEnterViewModel
import com.project.mypokedex.ui.viewmodels.GridPokemonScreenViewModel
import com.project.mypokedex.ui.viewmodels.SinglePokemonScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPokedexTheme {
                val animatedEnterViewModel: AnimatedEnterViewModel by viewModels()
                val singleScreenViewModel: SinglePokemonScreenViewModel by viewModels()
                val gridScreenViewModel: GridPokemonScreenViewModel by viewModels()
                val navController = rememberNavController()
                var topAppBarState by remember { mutableStateOf(TopAppBarStateHolder()) }
                MyPokedexApp(
                    animatedEnterState = animatedEnterViewModel.animatedEnterStateHolder.collectAsState().value,
                    topAppBarState = topAppBarState,
                    onNavigateBottomBar = { navigationRoute ->
                        navController.navigate(navigationRoute.route) {
                            launchSingleTop = true
                            popUpTo(navigationRoute.route)
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = AppDestination.GridScreen.route
                    ) {
                        composable(AppDestination.GridScreen.route) {
                            topAppBarState = gridScreenViewModel.topBarState.collectAsState().value
                            GridPokemonScreen(viewModel = gridScreenViewModel)
                        }

                        composable(AppDestination.SimpleScreen.route) {
                            topAppBarState = TopAppBarStateHolder()
                            SinglePokemonScreen(viewModel = singleScreenViewModel)
                        }
                    }
                }
            }
        }
    }
}


