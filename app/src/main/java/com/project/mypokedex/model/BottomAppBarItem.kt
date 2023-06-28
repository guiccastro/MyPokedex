package com.project.mypokedex.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.project.mypokedex.interfaces.Screen
import com.project.mypokedex.navigation.destinations.GridScreen
import com.project.mypokedex.navigation.destinations.ListScreen

enum class BottomAppBarItem(
    val label: String,
    val icon: ImageVector,
    val screen: Screen
) {
    GridScreenBottomAppBarItem(
        label = "Grid",
        icon = Icons.Default.List,
        screen = GridScreen
    ),

    ListScreenBottomAppBarItem(
        label = "List",
        icon = Icons.Default.AccountBox,
        screen = ListScreen
    );

    companion object {
        fun findByScreen(screen: Screen?): BottomAppBarItem? {
            return values().find { it.screen == screen }
        }
    }
}