package com.project.mypokedex.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.project.mypokedex.extensions.innerShadow
import com.project.mypokedex.ui.theme.Black
import com.project.mypokedex.ui.theme.CardColor
import com.project.mypokedex.ui.theme.MainBlue

@Composable
fun CardScreen(
    cardModifier: Modifier = Modifier,
    screenModifier: Modifier = Modifier,
    cardPadding: PaddingValues = PaddingValues(10.dp),
    cardBorderSize: PaddingValues = PaddingValues(10.dp),
    cardCorner: Dp = 8.dp,
    cardInternBackground: Color = MainBlue,
    screenCorner: Dp = 6.dp,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
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
            color = cardInternBackground,
            shape = RoundedCornerShape(screenCorner)
        ) {
            content()
        }
    }
}