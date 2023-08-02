package com.project.mypokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.mypokedex.navigation.MainNavComponent.Companion.AppNavHost
import com.project.mypokedex.ui.scaffold.MainScaffold
import com.project.mypokedex.ui.screens.AnimatedEnter
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.viewmodels.AnimatedEnterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyPokedexTheme {
                val animatedEnterViewModel: AnimatedEnterViewModel = hiltViewModel()
                AnimatedEnter(state = animatedEnterViewModel.animatedEnterUIState.collectAsState().value) {
                    MainScaffold {
                        AppNavHost()
                    }
                }
            }
        }
    }
}


