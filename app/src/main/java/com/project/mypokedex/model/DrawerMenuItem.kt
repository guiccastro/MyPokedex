package com.project.mypokedex.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.project.mypokedex.R
import com.project.mypokedex.interfaces.Screen
import com.project.mypokedex.navigation.screens.AboutScreen

enum class DrawerMenuItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val screen: Screen
) {
    SettingsDrawerMenuItem(
        title = R.string.drawer_item_settings_title,
        icon = R.drawable.ic_settings,
        screen = AboutScreen
    )
}
