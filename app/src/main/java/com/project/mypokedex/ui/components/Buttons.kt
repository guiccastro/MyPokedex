package com.project.mypokedex.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    containerColor: Color = MainWhite,
    enabled: Boolean = true,
) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(2.dp, MainBlack),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = containerColor.copy(alpha = 0.5F)
        ),
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        ResponsiveText(
            text = text,
            fontWeight = FontWeight(800),
            textStyle = PokemonGB,
            targetTextSizeHeight = 16.sp,
            color = BlackTextColor
        )
    }
}