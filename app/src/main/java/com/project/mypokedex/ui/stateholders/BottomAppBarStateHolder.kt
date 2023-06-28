package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.interfaces.BottomAppBarComponent
import com.project.mypokedex.model.BottomAppBarItem

data class BottomAppBarStateHolder(
    val selectedItem: BottomAppBarItem? = null,
    val bottomAppBarComponent: BottomAppBarComponent? = null
)
