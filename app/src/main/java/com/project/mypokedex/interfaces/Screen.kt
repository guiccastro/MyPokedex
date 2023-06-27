package com.project.mypokedex.interfaces

interface Screen : NavigationComponent {

    val topAppBarComponent: TopAppBarComponent?

    val bottomAppBarComponent: BottomAppBarComponent?
}