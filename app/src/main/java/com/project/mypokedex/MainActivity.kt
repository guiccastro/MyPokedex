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
import androidx.navigation.compose.rememberNavController
import com.project.mypokedex.navigation.MyPokedexNavHost
import com.project.mypokedex.navigation.navigateSingleTopWithPopUpTo
import com.project.mypokedex.ui.components.basic.MyPokedexApp
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.viewmodels.AnimatedEnterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPokedexTheme {
                val animatedEnterViewModel: AnimatedEnterViewModel by viewModels()
                val navController = rememberNavController()
                var topAppBarState by remember { mutableStateOf(TopAppBarStateHolder()) }
                MyPokedexApp(
                    animatedEnterState = animatedEnterViewModel.animatedEnterStateHolder.collectAsState().value,
                    topAppBarState = topAppBarState,
                    onNavigateBottomBar = { bottomAppBarItem ->
                        navController.navigateSingleTopWithPopUpTo(bottomAppBarItem)
                    }
                ) {
                    MyPokedexNavHost(
                        navController = navController,
                        onNewRoute = { newTopAppBarState ->
                            topAppBarState = newTopAppBarState
                        }
                    )
                }
            }
        }
    }
}


