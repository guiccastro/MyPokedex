package com.project.mypokedex.ui.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.project.mypokedex.ui.components.GameButtons
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
            .padding(vertical = 20.dp)
            .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical),
        verticalArrangement = Arrangement.Center,
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
        Spacer(modifier = Modifier.height(40.dp))
        OptionsButtons(state = state)
    }
}

@Composable
fun OptionsButtons(state: GameScreenUIState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        state.options.forEachIndexed { index, option ->
            val backgroundColor = state.buttonsUIState.getOrNull(index)?.first ?: MainWhite
            val isEnabled = state.buttonsUIState.getOrNull(index)?.second ?: true
            GameButtons(
                text = option.formattedName(),
                onClick = {
                    state.onOptionClick(option)
                },
                containerColor = backgroundColor,
                enabled = isEnabled
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        GameButtons(
            text = stringResource(R.string.game_screen_next_button).uppercase(),
            onClick = {
                state.onClickNext()
            },
            containerColor = MainBlue,
            modifier = Modifier
                .alpha(if (state.answered) 1F else 0F)
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