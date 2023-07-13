package com.project.mypokedex.model

import androidx.annotation.DrawableRes

data class TopAppBarActionItem(
    @DrawableRes val icon: Int,
    val onClick: () -> Unit
)
