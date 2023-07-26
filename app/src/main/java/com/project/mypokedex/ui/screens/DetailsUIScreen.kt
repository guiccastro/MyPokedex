package com.project.mypokedex.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.project.mypokedex.model.SpriteOption
import com.project.mypokedex.model.SpriteUtil
import com.project.mypokedex.sampledata.charizard
import com.project.mypokedex.ui.components.CardScreen
import com.project.mypokedex.ui.components.PokemonImage
import com.project.mypokedex.ui.components.PokemonTypeToUI
import com.project.mypokedex.ui.components.ResponsiveText
import com.project.mypokedex.ui.components.RotationalImage
import com.project.mypokedex.ui.stateholders.DetailsScreenUIState
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainRed
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
                    PokemonDetails(pokemon, state.pokemonImage)
                }

                spriteOrigin(
                    currentSpriteGroup = state.currentSpriteOrigin,
                    onClick = state.onSpriteOriginClick
                )

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

fun LazyListScope.spriteOrigin(
    currentSpriteGroup: SpriteUtil?,
    onClick: (SpriteUtil) -> Unit
) {
    if (currentSpriteGroup == null) return
    items(currentSpriteGroup.getSpritesOrigin()) {
        Row(
            modifier = Modifier
                .clickable {
                    onClick(it)
                }
        ) {
            Text(
                text = (it as SpriteOption).getName(),
                color = BlackTextColor,
                fontSize = 12.sp,
                style = PokemonGB,
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth()
            )

            Text(
                text = if (it == currentSpriteGroup || it.hasOnlySpriteOptions()) "Selecionar" else ">",
                color = BlackTextColor,
                fontSize = 12.sp,
                style = PokemonGB,
            )
        }
    }
}

@Composable
fun SectionTitle(@StringRes title: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        ResponsiveText(
            text = stringResource(id = title),
            textStyle = PokemonGB,
            color = BlackTextColor,
            targetTextSizeHeight = 14.sp,
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

fun LazyListScope.varieties(
    varieties: List<List<Pokemon>>,
    onPokemonClick: (Pokemon) -> Unit
) {
    if (varieties.isNotEmpty()) {
        item {
            SectionTitle(R.string.details_screen_varieties_title)
        }

        items(varieties) { row ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 100.dp)
                    .padding(vertical = 8.dp)
            ) {
                row.forEach { pokemon ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .weight(1F),
                        horizontalAlignment = CenterHorizontally
                    ) {
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
                        ResponsiveText(
                            text = pokemon.formattedName(),
                            textStyle = PokemonGB,
                            color = BlackTextColor,
                            targetTextSizeHeight = 7.sp,
                            fontWeight = FontWeight(1000),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }

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
        SectionTitle(title = R.string.details_screen_evolution_chain_title)
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    horizontalAlignment = CenterHorizontally
                ) {
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
                    ResponsiveText(
                        text = pokemon.formattedName(),
                        textStyle = PokemonGB,
                        color = BlackTextColor,
                        targetTextSizeHeight = 7.sp,
                        fontWeight = FontWeight(1000),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }


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
fun PokemonDetails(pokemon: Pokemon, pokemonImage: String) {
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
            frontImage = pokemonImage,
            backImage = "",
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