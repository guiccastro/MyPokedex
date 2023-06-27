package com.project.mypokedex.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.project.mypokedex.interfaces.Screen
import com.project.mypokedex.navigation.destinations.GridScreen
import com.project.mypokedex.navigation.destinations.ListScreen

sealed class BottomAppBarItem(
    val label: String,
    val icon: ImageVector,
    val screen: Screen
) {
    object GridScreenBottomAppBar :
        BottomAppBarItem(
            label = "Grid",
            icon = Icons.Default.List,
            screen = GridScreen
        )

    object ListScreenBottomAppBar : BottomAppBarItem(
        label = "List",
        icon = Icons.Default.AccountBox,
        screen = ListScreen
    )
}

val bottomAppBarItems = listOf(
    BottomAppBarItem.GridScreenBottomAppBar,
    BottomAppBarItem.ListScreenBottomAppBar,
)