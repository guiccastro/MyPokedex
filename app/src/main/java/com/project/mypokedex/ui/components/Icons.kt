package com.project.mypokedex.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.project.mypokedex.ui.theme.MainBlack

@Composable
fun AppIcon(
    @DrawableRes id: Int,
    modifier: Modifier = Modifier,
    iconColor: Color? = MainBlack,
    clickable: Boolean = false,
    onClick: () -> Unit = {},
    boxPadding: PaddingValues = PaddingValues(0.dp),
    size: Dp = 24.dp
) {
    val colorFilter = iconColor?.let { ColorFilter.tint(it) }
    val clickableModifier = if (clickable) {
        Modifier.clickable(onClick = onClick)
    } else {
        Modifier
    }

    Box(
        modifier = Modifier
            .padding(boxPadding)
    ) {
        Image(
            painter = painterResource(id = id),
            contentDescription = null,
            colorFilter = colorFilter,
            modifier = Modifier
                .size(size)
                .align(Alignment.Center)
                .then(clickableModifier)
                .then(modifier)
        )
    }

}