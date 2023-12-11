package com.project.mypokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainWhite
import com.project.mypokedex.ui.theme.PokemonGB

@Composable
fun AppNameCard(modifier: Modifier) {
    Box(
        modifier = modifier
    ) {
        ResponsiveText(
            text = stringResource(id = R.string.app_name),
            textStyle = PokemonGB,
            targetTextSizeHeight = 26.sp,
            color = BlackTextColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center)
                .background(MainWhite, RoundedCornerShape(6.dp))
                .border(4.dp, MainBlack, RoundedCornerShape(6.dp))
                .padding(vertical = 10.dp, horizontal = 20.dp)
        )
    }
}