package com.project.mypokedex.ui.components.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.ui.components.bottomBorder
import com.project.mypokedex.ui.components.customShadow
import com.project.mypokedex.ui.stateholders.GridPokemonScreenStateHolder
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.PokemonGB

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(state: GridPokemonScreenStateHolder) {
    TopAppBar(
        modifier = Modifier
            .bottomBorder(1.dp, BorderBlack)
            .customShadow(
                color = Color.Black.copy(alpha = 0.7f),
                blurRadius = 5.dp,
                widthOffset = 10.dp
            ),
        title = {
            Text(
                text = "MyPokedex",
                style = PokemonGB,
                color = BorderBlack,
                fontSize = 14.sp
            )
        },
        actions = {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                colorFilter = ColorFilter.tint(BorderBlack),
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable(
                        onClick = state.onSearchClick
                    )
            )
        }
    )
}