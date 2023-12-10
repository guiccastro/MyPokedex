package com.project.mypokedex.navigation.screens

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.project.mypokedex.R
import com.project.mypokedex.interfaces.BottomAppBarComponent
import com.project.mypokedex.interfaces.Screen
import com.project.mypokedex.interfaces.TopAppBarComponent
import com.project.mypokedex.model.BottomAppBarItem
import com.project.mypokedex.model.TopAppBarActionItem
import com.project.mypokedex.ui.screens.AboutUIScreen

object AboutScreen : Screen {
    private const val aboutRoute = "AboutScreen"

    override val topAppBarComponent: TopAppBarComponent = object : TopAppBarComponent {
        override fun getTitle(): Int = R.string.about_screen_title

        override fun hasMenu(): Boolean = true

        override fun hasReturn(): Boolean = false

        override fun getActionItems(): List<TopAppBarActionItem> = emptyList()
    }
    override val bottomAppBarComponent: BottomAppBarComponent = object : BottomAppBarComponent {
        override fun getItems(): List<BottomAppBarItem> {
            return BottomAppBarItem.HomeBottomAppBarItems
        }
    }

    override fun NavGraphBuilder.screen() {
        composable(aboutRoute) {
            AboutUIScreen()
        }
    }

    override fun getRoute(): String = aboutRoute

    override fun NavController.navigateToItself(pokemonId: Int?, navOptions: NavOptions?) =
        navigate(aboutRoute, navOptions)
}