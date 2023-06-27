package com.project.mypokedex.interfaces

import com.project.mypokedex.model.TopAppBarItem

interface TopAppBarComponent {

    fun getTitle(): String

    fun hasReturn(): Boolean

    fun getItems(): List<TopAppBarItem>
}