package com.project.mypokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainWhite
import com.project.mypokedex.ui.theme.PokemonGB

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textSize: TextUnit = 12.sp,
    containerColor: Color = MainWhite,
    enabled: Boolean = true,
    borderWidth: Dp = 2.dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                if (enabled) containerColor else containerColor.copy(alpha = containerColor.alpha / 2),
                RoundedCornerShape(6.dp)
            )
            .border(borderWidth, MainBlack, RoundedCornerShape(6.dp))
            .clickable(
                enabled = enabled
            ) {
                onClick()
            }
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            fontWeight = FontWeight(800),
            style = PokemonGB,
            color = BlackTextColor,
            modifier = modifier,
            fontSize = textSize
        )
    }
}