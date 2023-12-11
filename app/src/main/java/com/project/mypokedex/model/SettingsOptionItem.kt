package com.project.mypokedex.model

import androidx.annotation.StringRes
import com.project.mypokedex.interfaces.Screen

data class SettingsOptionItem(
    @StringRes val title: Int,
    val screen: Screen
)
