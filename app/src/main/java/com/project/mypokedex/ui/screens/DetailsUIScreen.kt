package com.project.mypokedex.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.project.mypokedex.model.GroupSprite
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.SelectableSprite
import com.project.mypokedex.model.Sprite
import com.project.mypokedex.model.SpriteGender
import com.project.mypokedex.model.SpriteTypes
import com.project.mypokedex.model.SpriteVariant
import com.project.mypokedex.model.Sprites
import com.project.mypokedex.sampledata.bulbasaur
import com.project.mypokedex.sampledata.charizard
import com.project.mypokedex.sampledata.charmander
import com.project.mypokedex.sampledata.squirtle
import com.project.mypokedex.ui.components.CardScreen
import com.project.mypokedex.ui.components.PokemonGenerationToUI
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
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            state.pokemon?.let { pokemon ->

                basicDetails(
                    pokemon = pokemon,
                    frontImage = state.pokemonFrontImage,
                    backImage = state.pokemonBackImage
                )

                spriteTypes(
                    spriteGenderOptions = state.spriteGenderOptions,
                    spriteVariantOptions = state.spriteVariantOptions,
                    onSpriteTypeClick = state.onSpriteTypeClick
                )

                sprites(
                    selectableSpriteOptions = state.selectableSpriteOptions,
                    spriteGroupOptions = state.spriteGroupOptions,
                    onSelectableSpriteOptionClick = state.onSelectableSpriteOptionClick,
                    onSpriteGroupOptionClick = state.onSpriteGroupOptionClick,
                    hasReturn = state.hasReturnSprite,
                    onReturnSpritesClick = state.onReturnSpritesClick
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

fun LazyListScope.spriteTypes(
    spriteGenderOptions: SpriteGender?,
    spriteVariantOptions: SpriteVariant?,
    onSpriteTypeClick: (SpriteTypes) -> Unit
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            spriteGenderOptions?.let {
                Text(
                    text = spriteGenderOptions.toString(),
                    color = BlackTextColor,
                    fontSize = 12.sp,
                    style = PokemonGB,
                    modifier = Modifier
                        .border(1.dp, MainBlack, RoundedCornerShape(4.dp))
                        .clickable {
                            onSpriteTypeClick(it as SpriteTypes)
                        }
                        .padding(6.dp)
                )
            }
            spriteVariantOptions?.let {
                Text(
                    text = spriteVariantOptions.toString(),
                    color = BlackTextColor,
                    fontSize = 12.sp,
                    style = PokemonGB,
                    modifier = Modifier
                        .border(1.dp, MainBlack, RoundedCornerShape(4.dp))
                        .clickable {
                            onSpriteTypeClick(it as SpriteTypes)
                        }
                        .padding(6.dp)
                )
            }
        }
    }
}

fun LazyListScope.sprites(
    selectableSpriteOptions: List<SelectableSprite>,
    spriteGroupOptions: List<GroupSprite>,
    onSelectableSpriteOptionClick: (SelectableSprite) -> Unit,
    onSpriteGroupOptionClick: (Sprite) -> Unit,
    hasReturn: Boolean,
    onReturnSpritesClick: () -> Unit
) {
    item {
        SectionTitle(title = R.string.details_screen_sprites_title)
    }

    item {
        if (hasReturn) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .padding(vertical = 2.dp)
                    .height(30.dp)
                    .border(1.dp, MainBlack, RoundedCornerShape(4.dp))
                    .clickable {
                        onReturnSpritesClick()
                    }
                    .padding(horizontal = 16.dp)
            )
        }
    }

    item {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
        ) {
            selectableSpriteOptions.forEach {
                Row(
                    modifier = Modifier
                        .height(34.dp)
                        .padding(vertical = 2.dp)
                        .clickable {
                            onSelectableSpriteOptionClick(it)
                        }
                        .border(1.dp, MainBlack, RoundedCornerShape(4.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ResponsiveText(
                        text = stringResource(id = it.getName()),
                        color = BlackTextColor,
                        targetTextSizeHeight = 14.sp,
                        textStyle = PokemonGB,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth()
                            .padding(start = 4.dp)
                    )

                    ResponsiveText(
                        text = stringResource(id = R.string.details_screen_sprites_select),
                        color = BlackTextColor,
                        targetTextSizeHeight = 14.sp,
                        textStyle = PokemonGB,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(end = 4.dp)
                    )
                }
            }
        }
    }

    item {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 20.dp)
        ) {
            spriteGroupOptions.forEach {
                Row(
                    modifier = Modifier
                        .height(34.dp)
                        .padding(vertical = 2.dp)
                        .clickable {
                            onSpriteGroupOptionClick(it)
                        }
                        .border(1.dp, MainBlack, RoundedCornerShape(4.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ResponsiveText(
                        text = stringResource(id = it.getName()),
                        color = BlackTextColor,
                        targetTextSizeHeight = 14.sp,
                        textStyle = PokemonGB,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_simple_arrow_forward),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .padding(horizontal = 4.dp)
                    )
                }
            }
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
    evolutionChain: List<List<Pokemon>>,
    onPokemonClick: (Pokemon) -> Unit
) {
    if (evolutionChain.isEmpty()) return

    item {
        SectionTitle(title = R.string.details_screen_evolution_chain_title)
    }

    items(evolutionChain) { rowEvolution ->
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

fun LazyListScope.basicDetails(pokemon: Pokemon, frontImage: String, backImage: String) {
    item {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
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

                // Pokemon Generation
                pokemon.species?.generation?.let {
                    PokemonGenerationToUI(
                        pokemonGeneration = it,
                        fontSize = 8.sp
                    )
                }
            }


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

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Pokemon Types
                pokemon.types.forEach {
                    PokemonTypeToUI(
                        pokemonType = it,
                        size = 34.dp
                    )
                }
            }

            // Pokemon Images
            RotationalImage(
                frontImage = frontImage,
                backImage = backImage,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(CenterHorizontally),
                backgroundType = BackgroundType.RadialBackground(
                    White.copy(alpha = 0.5F),
                    Transparent
                )
            )
        }
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
                    evolutionChain = listOf(listOf(bulbasaur, squirtle, charmander)),
                    selectableSpriteOptions = listOf(Sprites()),
                    spriteGroupOptions = listOf(Sprites()),
                    spriteGenderOptions = SpriteTypes.Male,
                    spriteVariantOptions = SpriteTypes.Normal,
                    varieties = listOf(listOf(bulbasaur, squirtle, charmander))
                )
            )
        }
    }
}