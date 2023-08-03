package com.project.mypokedex.ui.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.mypokedex.R
import com.project.mypokedex.model.BackgroundType
import com.project.mypokedex.sampledata.charmander
import com.project.mypokedex.sampledata.listPokemons
import com.project.mypokedex.ui.components.AppButton
import com.project.mypokedex.ui.components.PokemonImage
import com.project.mypokedex.ui.stateholders.GameScreenUIState
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainBlue
import com.project.mypokedex.ui.theme.MainWhite
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.Transparent
import com.project.mypokedex.ui.theme.White

@Composable
fun GameUIScreen(state: GameScreenUIState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, bottom = 10.dp)
            .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = CenterHorizontally
    ) {
        PokemonImage(
            url = state.pokemon?.getGifOrImage(),
            clickable = state.answered,
            onClick = { state.onClickPokemon() },
            imageColorFilter = if (!state.answered) ColorFilter.tint(MainBlack) else null,
            modifier = Modifier
                .fillMaxSize(0.5F)
                .weight(1F),
            backgroundType = BackgroundType.RadialBackground(White.copy(alpha = 0.5F), Transparent)
        )
        OptionsButtons(state = state, modifier = Modifier.weight(1F))
    }
}

@Composable
fun OptionsButtons(state: GameScreenUIState, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .heightIn(max = 180.dp)
            .padding(horizontal = 40.dp)
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        state.options.forEachIndexed { index, option ->
            val backgroundColor = state.buttonsUIState.getOrNull(index)?.first ?: MainWhite
            val isEnabled = state.buttonsUIState.getOrNull(index)?.second ?: true
            AppButton(
                text = option.formattedName(),
                onClick = {
                    state.onOptionClick(option)
                },
                containerColor = backgroundColor,
                enabled = isEnabled,
                modifier = Modifier
                    .weight(1F)
            )
        }

        AppButton(
            text = stringResource(R.string.game_screen_next_button).uppercase(),
            onClick = {
                state.onClickNext()
            },
            enabled = state.answered,
            containerColor = MainBlue,
            modifier = Modifier
                .alpha(if (state.answered) 1F else 0F)
                .weight(1F)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun GameUIScreenPreview() {
    MyPokedexTheme {
        Surface {
            GameUIScreen(state = GameScreenUIState(pokemon = charmander, options = listPokemons))
        }
    }
}