package com.project.mypokedex.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.project.mypokedex.ui.theme.Black
import com.project.mypokedex.ui.theme.CardColor
import com.project.mypokedex.ui.theme.CardInternBackground

@Composable
fun CardScreen(
    cardModifier: Modifier = Modifier,
    screenModifier: Modifier = Modifier,
    cardPadding: PaddingValues = PaddingValues(10.dp),
    cardBorderSize: PaddingValues = PaddingValues(10.dp),
    cardCorner: Dp = 8.dp,
    screenCorner: Dp = 6.dp,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(cardPadding)
            .then(cardModifier),
        color = CardColor,
        shape = RoundedCornerShape(cardCorner)
    ) {
        Surface(
            modifier = Modifier
                .padding(cardBorderSize)
                .innerShadow(
                    color = Black,
                    cornersRadius = screenCorner,
                    blur = 5.dp
                )
                .then(screenModifier),
            color = CardInternBackground,
            shape = RoundedCornerShape(screenCorner)
        ) {
            content()
        }
    }
}