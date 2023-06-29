package com.project.mypokedex

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.project.mypokedex.model.BottomAppBarItem
import com.project.mypokedex.navigation.MainNavComponent
import com.project.mypokedex.navigation.MainNavComponent.Companion.navController
import com.project.mypokedex.ui.stateholders.BottomAppBarUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object MainBottomAppBarComponent {

    private var bottomAppBarState: MutableStateFlow<BottomAppBarUIState> =
        MutableStateFlow(BottomAppBarUIState())

    @Composable
    fun bottomAppBarState(): BottomAppBarUIState {
        return bottomAppBarState.asStateFlow().collectAsState().value
    }

    fun updateBottomAppBarState(newState: BottomAppBarUIState) {
        bottomAppBarState.value = newState
    }

    fun onClickBottomAppBarItem(bottomAppBarItem: BottomAppBarItem) {
        navController.apply {
            bottomAppBarItem.screen.apply {
                navigateToItself(navOptions = MainNavComponent.getSingleTopWithPopUpTo(getRoute()))
            }
        }
    }
}