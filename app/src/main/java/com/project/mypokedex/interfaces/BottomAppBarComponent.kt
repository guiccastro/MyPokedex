package com.project.mypokedex.interfaces

import com.project.mypokedex.model.BottomAppBarItem

interface BottomAppBarComponent {

    fun getItems(): List<BottomAppBarItem>
}