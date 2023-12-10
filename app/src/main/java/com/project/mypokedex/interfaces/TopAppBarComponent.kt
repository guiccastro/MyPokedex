package com.project.mypokedex.interfaces

import androidx.annotation.StringRes
import com.project.mypokedex.model.TopAppBarActionItem

interface TopAppBarComponent {

    @StringRes
    fun getTitle(): Int

    fun hasMenu(): Boolean

    fun hasReturn(): Boolean

    fun getActionItems(): List<TopAppBarActionItem>
}