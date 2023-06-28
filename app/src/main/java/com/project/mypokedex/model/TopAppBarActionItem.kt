package com.project.mypokedex.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class TopAppBarActionItem(
    @DrawableRes val icon: Int,
    val iconColor: Color,
    val onClick: () -> Unit
)
