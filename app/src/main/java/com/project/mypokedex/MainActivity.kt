package com.project.mypokedex

import android.annotation.SuppressLint
import android.app.LocaleManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.collectAsState
import androidx.core.os.LocaleListCompat
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.mypokedex.navigation.MainNavComponent.Companion.AppNavHost
import com.project.mypokedex.ui.scaffold.MainScaffold
import com.project.mypokedex.ui.screens.AnimatedEnter
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.viewmodels.AnimatedEnterViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        localeSelection(context = baseContext, localeTag = Locale("pt").toLanguageTag())

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

    fun localeSelection(context: Context, localeTag: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java).applicationLocales =
                LocaleList.forLanguageTags(localeTag)
        } else {
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(localeTag)
            )
        }
    }
}


