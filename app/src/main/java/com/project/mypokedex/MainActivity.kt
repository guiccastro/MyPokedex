package com.project.mypokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.mypokedex.navigation.MainNavComponent.Companion.AppNavHost
import com.project.mypokedex.ui.scaffold.MainScaffold
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.viewmodels.AnimatedEnterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPokedexTheme {
                val animatedEnterViewModel: AnimatedEnterViewModel = hiltViewModel()

                MainScaffold(
                    animatedEnterState = animatedEnterViewModel.animatedEnterUIState.collectAsState().value
                ) {
                    AppNavHost()
                }
            }
        }
    }
}


