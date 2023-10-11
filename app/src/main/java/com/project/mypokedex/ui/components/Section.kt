package com.project.mypokedex.ui.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.model.HeaderState
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB

@Composable
fun Section(
    @StringRes title: Int,
    content: @Composable () -> Unit
) {
    var headerState by remember { mutableStateOf(HeaderState.HideContent) }
    SectionHeader(title, headerState) { headerState = headerState.switchState() }

    AnimatedVisibility(
        visible = headerState == HeaderState.ShowContent,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        content()
    }
}

@Composable
fun SectionHeader(
    @StringRes title: Int,
    headerState: HeaderState,
    onHeaderClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(radius = 0.dp)
            ) {
                onHeaderClick()
            }
    ) {
        ResponsiveText(
            text = stringResource(id = title),
            textStyle = PokemonGB,
            color = BlackTextColor,
            targetTextSizeHeight = 14.sp,
            fontWeight = FontWeight(1000)
        )

        Divider(
            thickness = 1.dp,
            color = MainBlack,
            modifier = Modifier
                .weight(1F)
                .padding(start = 4.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_arrow_drop_down),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MainBlack),
            modifier = Modifier
                .size(32.dp)
                .graphicsLayer {
                    rotationX = if (headerState == HeaderState.ShowContent) 180F else 0F
                }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewSection() {
    MyPokedexTheme {
        Surface {
            Section(R.string.details_screen_evolution_chain_title) {

            }
        }
    }
}