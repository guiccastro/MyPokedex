package com.project.mypokedex.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

sealed class BackgroundType {
    class RadialBackground(val color1: Color, val color2: Color) : BackgroundType()
    class ImageBackground(
        @DrawableRes val id: Int,
        val color1: Color? = null,
        val color2: Color? = null
    ) : BackgroundType()

    object None : BackgroundType()
}
