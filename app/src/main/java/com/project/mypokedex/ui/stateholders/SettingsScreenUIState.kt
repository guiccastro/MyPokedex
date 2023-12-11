package com.project.mypokedex.ui.stateholders

import com.project.mypokedex.model.SettingsOptionItem

data class SettingsScreenUIState(
    val options: List<SettingsOptionItem> = emptyList()
)
