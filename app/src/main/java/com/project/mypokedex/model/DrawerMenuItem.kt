package com.project.mypokedex.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.project.mypokedex.R
import com.project.mypokedex.interfaces.Screen
import com.project.mypokedex.navigation.screens.AboutScreen
import com.project.mypokedex.navigation.screens.SettingsScreen

enum class DrawerMenuItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val screen: Screen
) {
    SettingsDrawerMenuItem(
        title = R.string.drawer_item_settings_title,
        icon = R.drawable.ic_settings,
        screen = SettingsScreen
    ),

    AboutAppDrawerMenuItem(
        title = R.string.about_screen_label,
        icon = R.drawable.ic_about_app,
        screen = AboutScreen
    )
}
