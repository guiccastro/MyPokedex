package com.project.mypokedex.ui.scaffold.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.sampledata.actionItemsSample
import com.project.mypokedex.ui.components.bottomBorder
import com.project.mypokedex.ui.components.customShadow
import com.project.mypokedex.ui.stateholders.TopAppBarUIState
import com.project.mypokedex.ui.theme.Black
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.PokemonGB

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(state: TopAppBarUIState = TopAppBarUIState()) {
    TopAppBar(
        modifier = Modifier
            .bottomBorder(1.dp, BorderBlack)
            .customShadow(
                color = Black.copy(alpha = 0.7f),
                blurRadius = 5.dp,
                widthOffset = 10.dp
            ),
        title = {
            Text(
                text = stringResource(state.title),
                style = PokemonGB,
                color = BorderBlack,
                fontSize = 14.sp
            )
        },
        navigationIcon = {
            if (state.hasReturn) {
                Image(
                    painter = painterResource(id = R.drawable.ic_return),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(BorderBlack),
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable(
                            onClick = state.onClickReturn
                        )
                )
            }
        },
        actions = {
            state.actionItems.forEach { item ->
                Image(
                    painter = painterResource(id = item.icon),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(item.iconColor),
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable(
                            onClick = item.onClick
                        )
                )
            }
        }
    )
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar(
        TopAppBarUIState(
            hasReturn = true,
            actionItems = actionItemsSample
        )
    )
}