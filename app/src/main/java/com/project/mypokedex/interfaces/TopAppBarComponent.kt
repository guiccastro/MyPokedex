package com.project.mypokedex.interfaces

import com.project.mypokedex.model.TopAppBarActionItem

interface TopAppBarComponent {

    fun getTitle(): String

    fun hasReturn(): Boolean

    fun getActionItems(): List<TopAppBarActionItem>
}