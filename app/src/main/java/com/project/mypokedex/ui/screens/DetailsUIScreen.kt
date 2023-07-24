package com.project.mypokedex.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.model.BackgroundType
import com.project.mypokedex.model.EvolutionChain
import com.project.mypokedex.model.EvolutionChainItem
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.sampledata.charizard
import com.project.mypokedex.ui.components.CardScreen
import com.project.mypokedex.ui.components.PokemonImage
import com.project.mypokedex.ui.components.PokemonTypeToUI
import com.project.mypokedex.ui.components.ResponsiveText
import com.project.mypokedex.ui.components.RotationalImage
import com.project.mypokedex.ui.stateholders.DetailsScreenUIState
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.theme.Transparent
import com.project.mypokedex.ui.theme.White

@Composable
fun DetailsUIScreen(state: DetailsScreenUIState) {
    CardScreen {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 10.dp)
        ) {
            state.pokemon?.let { pokemon ->
                item {
                    PokemonDetails(pokemon)
                }

                evolutionChain(
                    evolutionChain = state.evolutionChain,
                    onPokemonClick = state.onPokemonClick
                )

                varieties(
                    varieties = state.varieties,
                    onPokemonClick = state.onPokemonClick
                )
            }
        }
    }
}

fun LazyListScope.varieties(
    varieties: List<Pokemon>,
    onPokemonClick: (Pokemon) -> Unit
) {
    if (varieties.isNotEmpty()) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Varieties",
                    style = PokemonGB,
                    color = BlackTextColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(1000),
                    modifier = Modifier
                        .padding(bottom = 6.dp, end = 4.dp)
                )

                Divider(
                    modifier = Modifier,
                    thickness = 1.dp,
                    color = MainBlack
                )
            }
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 100.dp)
                    .padding(vertical = 8.dp)
            ) {
                varieties.forEach { pokemon ->
                    PokemonImage(
                        url = pokemon.getGifOrImage(),
                        backgroundType = BackgroundType.None,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F),
                        clickable = true,
                        onClick = {
                            onPokemonClick(pokemon)
                        }
                    )
                }
            }
        }
    }
}


fun LazyListScope.evolutionChain(
    evolutionChain: EvolutionChain?,
    onPokemonClick: (Pokemon) -> Unit
) {
    if (evolutionChain == null) return

    item {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.details_screen_evolution_chain_title),
                style = PokemonGB,
                color = BlackTextColor,
                fontSize = 14.sp,
                fontWeight = FontWeight(1000),
                modifier = Modifier
                    .padding(bottom = 6.dp, end = 4.dp)
            )

            Divider(
                modifier = Modifier,
                thickness = 1.dp,
                color = MainBlack
            )
        }
    }

    items(evolutionChain.getAllPaths()) { rowEvolution ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 100.dp)
                .padding(vertical = 8.dp)
        ) {
            rowEvolution.forEach { pokemon ->
                PokemonImage(
                    url = pokemon.getGifOrImage(),
                    backgroundType = BackgroundType.None,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    clickable = true,
                    onClick = {
                        onPokemonClick(pokemon)
                    }
                )

                if (rowEvolution.last() != pokemon) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_forward),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                    )
                }
            }
        }
    }

    item {
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
    }
}

@Composable
fun PokemonDetails(pokemon: Pokemon) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.Center
    ) {

        // Pokemon ID
        Text(
            text = pokemon.formattedID(),
            fontSize = 14.sp,
            fontWeight = FontWeight(500),
            color = BlackTextColor,
            modifier = Modifier,
            textAlign = TextAlign.Center,
            style = PokemonGB
        )

        // Pokemon Name
        ResponsiveText(
            text = pokemon.formattedName(),
            targetTextSizeHeight = 16.sp,
            fontWeight = FontWeight(1000),
            color = BlackTextColor,
            modifier = Modifier
                .padding(vertical = 4.dp),
            textAlign = TextAlign.Center,
            textStyle = PokemonGB,
            maxLines = 1
        )

        // Pokemon Types
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            pokemon.types.forEach {
                PokemonTypeToUI(
                    pokemonType = it,
                    size = 34.dp
                )
            }
        }

        // Pokemon Images
        RotationalImage(
            frontImage = pokemon.getGifOrImage(),
            backImage = pokemon.backGif,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(CenterHorizontally),
            backgroundType = BackgroundType.RadialBackground(White.copy(alpha = 0.5F), Transparent)
        )
    }
}

@Preview
@Composable
fun DetailsUIScreenPreview() {
    MyPokedexTheme {
        Surface {
            DetailsUIScreen(
                state = DetailsScreenUIState(
                    pokemon = charizard,
                    evolutionChain = EvolutionChain(
                        chain = EvolutionChainItem(
                            pokemon = charizard,
                            evolvesTo = emptyList()
                        )
                    )
                )
            )
        }
    }
}