package com.project.mypokedex.ui.scaffold.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.sampledata.actionItemsSample
import com.project.mypokedex.ui.bottomBorder
import com.project.mypokedex.ui.components.AppIcon
import com.project.mypokedex.ui.customShadow
import com.project.mypokedex.ui.stateholders.TopAppBarUIState
import com.project.mypokedex.ui.theme.Black
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(state: TopAppBarUIState = TopAppBarUIState()) {
    TopAppBar(
        modifier = Modifier
            .bottomBorder(1.dp, MainBlack)
            .customShadow(
                color = Black.copy(alpha = 0.7f),
                blurRadius = 5.dp,
                widthOffset = 10.dp
            ),
        title = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Text(
                    text = stringResource(state.title),
                    style = PokemonGB,
                    color = BlackTextColor,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                )
            }
        },
        navigationIcon = {
            if (state.hasReturn) {
                AppIcon(
                    id = R.drawable.ic_return,
                    clickable = true,
                    onClick = state.onClickReturn,
                    boxPadding = PaddingValues(horizontal = 10.dp)
                )
            }
        },
        actions = {
            state.actionItems.forEach { item ->
                AppIcon(
                    id = item.icon,
                    clickable = true,
                    onClick = item.onClick,
                    boxPadding = PaddingValues(horizontal = 10.dp)
                )
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun TopBarPreview() {
    MyPokedexTheme {
        Scaffold(
            topBar = {
                TopBar(
                    TopAppBarUIState(
                        hasReturn = true,
                        actionItems = actionItemsSample
                    )
                )
            }
        ) {
            Box(modifier = Modifier.padding(it))
        }

    }
}