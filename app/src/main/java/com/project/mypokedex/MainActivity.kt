package com.project.mypokedex

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
class MainActivity : AppCompatActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            MyPokedexTheme {
                MainScaffold {
                    AppNavHost()
                }

                val animatedEnterViewModel: AnimatedEnterViewModel = hiltViewModel()
                AnimatedEnter(state = animatedEnterViewModel.uiState.collectAsState().value)
            }
        }
    }


}


