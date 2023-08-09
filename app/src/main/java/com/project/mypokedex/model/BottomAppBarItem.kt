package com.project.mypokedex.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.project.mypokedex.R
import com.project.mypokedex.interfaces.Screen
import com.project.mypokedex.navigation.screens.AboutScreen
import com.project.mypokedex.navigation.screens.GameScreen
import com.project.mypokedex.navigation.screens.GridScreen
import com.project.mypokedex.navigation.screens.ListScreen

enum class BottomAppBarItem(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    val screen: Screen
) {
    GridScreenBottomAppBarItem(
        label = R.string.grid_screen_label,
        icon = R.drawable.ic_grid,
        screen = GridScreen
    ),

    ListScreenBottomAppBarItem(
        label = R.string.list_screen_label,
        icon = R.drawable.ic_list,
        screen = ListScreen
    ),

    GameScreenBottomAppBarItem(
        label = R.string.game_screen_label,
        icon = R.drawable.ic_game,
        screen = GameScreen
    ),

    AboutScreenBottomAppBarItem(
        label = R.string.about_screen_label,
        icon = R.drawable.ic_about_app,
        screen = AboutScreen
    );

    companion object {
        fun findByScreen(screen: Screen?): BottomAppBarItem? {
            return values().find { it.screen == screen }
        }

        val HomeBottomAppBarItems = listOf(
            GridScreenBottomAppBarItem,
            ListScreenBottomAppBarItem,
            GameScreenBottomAppBarItem,
            AboutScreenBottomAppBarItem
        )
    }
}