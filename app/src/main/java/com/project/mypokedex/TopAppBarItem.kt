package com.project.mypokedex

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class TopAppBarItem(
    @DrawableRes val icon: Int,
    val iconColor: Color,
    val onClickEvent: () -> Unit
)
