package com.project.mypokedex.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.project.mypokedex.R
import com.project.mypokedex.interfaces.Screen
import com.project.mypokedex.navigation.screens.GameScreen
import com.project.mypokedex.navigation.screens.GridScreen
import com.project.mypokedex.navigation.screens.ListScreen

enum class BottomAppBarItem(
    @StringRes val label: Int,
    val icon: ImageVector,
    val screen: Screen
) {
    GridScreenBottomAppBarItem(
        label = R.string.grid_screen_label,
        icon = Icons.Default.List,
        screen = GridScreen
    ),

    ListScreenBottomAppBarItem(
        label = R.string.list_screen_label,
        icon = Icons.Default.AccountBox,
        screen = ListScreen
    ),

    GameScreenBottomAppBarItem(
        label = R.string.game_screen_label,
        icon = Icons.Default.AccountBox,
        screen = GameScreen
    );

    companion object {
        fun findByScreen(screen: Screen?): BottomAppBarItem? {
            return values().find { it.screen == screen }
        }

        val HomeBottomAppBarItems = listOf(
            GridScreenBottomAppBarItem,
            ListScreenBottomAppBarItem,
            GameScreenBottomAppBarItem
        )
    }
}