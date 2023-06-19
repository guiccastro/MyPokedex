package com.project.mypokedex

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomAppBarItem(
    val label: String,
    val icon: ImageVector
) {
    object GridScreen :
        BottomAppBarItem(
            label = "Grid",
            icon = Icons.Default.List
        )

    object ListScreen : BottomAppBarItem(
        label = "List",
        icon = Icons.Default.AccountBox
    )
}

val bottomAppBarItems = listOf(
    BottomAppBarItem.GridScreen,
    BottomAppBarItem.ListScreen,
)