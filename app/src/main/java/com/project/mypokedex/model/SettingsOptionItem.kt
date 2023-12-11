package com.project.mypokedex.model

import androidx.annotation.StringRes
import com.project.mypokedex.R
import com.project.mypokedex.interfaces.Screen
import com.project.mypokedex.navigation.screens.LanguageScreen

enum class SettingsOptionItem(
    @StringRes val title: Int,
    val screen: Screen
) {
    LanguageSettingItem(
        title = R.string.settings_language_title,
        screen = LanguageScreen
    )
}
