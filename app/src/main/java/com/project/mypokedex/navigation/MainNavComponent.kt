package com.project.mypokedex.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.project.mypokedex.interfaces.Screen
import com.project.mypokedex.model.BottomAppBarItem
import com.project.mypokedex.model.MainBottomAppBarComponent.updateBottomAppBarState
import com.project.mypokedex.model.MainDrawerMenuComponent
import com.project.mypokedex.model.MainDrawerMenuComponent.updateDrawerMenuState
import com.project.mypokedex.model.MainTopAppBarComponent.updateTopAppBarState
import com.project.mypokedex.navigation.screens.DetailsScreen
import com.project.mypokedex.navigation.screens.HomeGroupScreen
import com.project.mypokedex.navigation.screens.SettingsGroupScreen
import com.project.mypokedex.ui.stateholders.BottomAppBarUIState
import com.project.mypokedex.ui.stateholders.DrawerMenuUIState
import com.project.mypokedex.ui.stateholders.TopAppBarUIState

class MainNavComponent private constructor() {

    private lateinit var _navController: NavHostController

    companion object : NavController.OnDestinationChangedListener {

        @Volatile
        private var instance: MainNavComponent? = null

        private fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: MainNavComponent().also { instance = it }
            }

        private val screensList = listOf<Screen>(
            DetailsScreen
        )

        private val groupNavigation = listOf(
            HomeGroupScreen,
            SettingsGroupScreen
        )

        val navController: NavHostController get() = getInstance()._navController

        @Composable
        fun AppNavHost() {
            getInstance()._navController = rememberNavController()

            LaunchedEffect(Unit) {
                navController.addOnDestinationChangedListener(this@Companion)
            }

            NavHost(
                navController = navController,
                startDestination = HomeGroupScreen.getRoute()
            ) {
                groupNavigation.forEach {
                    it.apply {
                        graph()
                    }
                }
                screensList.forEach {
                    it.apply {
                        screen()
                    }
                }
            }
        }

        override fun onDestinationChanged(
            controller: NavController,
            destination: NavDestination,
            arguments: Bundle?
        ) {
            val route = destination.route?.split("/")?.first() ?: ""
            val screen = getScreen(route)

            updateTopBar(screen)
            updateBottomBar(screen)
            updateDrawerMenu(screen)
        }

        private fun updateTopBar(screen: Screen?) {
            val topAppBarComponent = screen?.topAppBarComponent
            updateTopAppBarState(
                topAppBarComponent?.let {
                    TopAppBarUIState(
                        title = topAppBarComponent.getTitle(),
                        hasMenu = topAppBarComponent.hasMenu(),
                        hasReturn = topAppBarComponent.hasReturn(),
                        onClickReturn = { navController.popBackStack() },
                        actionItems = topAppBarComponent.getActionItems()
                    )
                } ?: run {
                    TopAppBarUIState()
                }
            )
        }

        private fun updateBottomBar(screen: Screen?) {
            val bottomAppBarItemSelected = BottomAppBarItem.findByScreen(screen)
            updateBottomAppBarState(
                BottomAppBarUIState(
                    selectedItem = bottomAppBarItemSelected,
                    bottomAppBarComponent = screen?.bottomAppBarComponent
                )
            )
        }

        private fun updateDrawerMenu(screen: Screen?) {
            val drawerItemSelected = MainDrawerMenuComponent.findItemByScreen(screen)
            updateDrawerMenuState(
                DrawerMenuUIState(
                    itemSelected = drawerItemSelected
                )
            )
        }

        fun getAllScreens(): List<Screen> {
            val screens = ArrayList<Screen>()
            screens.addAll(screensList)
            groupNavigation.forEach { screens.addAll(it.listScreens) }
            return screens
        }

        fun getScreen(route: String): Screen? {
            return getAllScreens().find {
                it.getRoute() == route
            }
        }

        fun getSingleTopWithPopUpTo(route: String): NavOptions {
            return navOptions {
                launchSingleTop = true
                popUpTo(route)
            }
        }
    }
}