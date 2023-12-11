package com.project.mypokedex.ui.stateholders

import androidx.compose.material3.DrawerValue
import com.project.mypokedex.model.DrawerMenuItem

data class DrawerMenuUIState(
    val itemSelected: DrawerMenuItem? = null,
    val drawerValue: DrawerValue = DrawerValue.Closed
)
