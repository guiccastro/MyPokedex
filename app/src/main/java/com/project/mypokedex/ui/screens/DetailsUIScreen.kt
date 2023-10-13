package com.project.mypokedex.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.SubcomposeAsyncImage
import coil.imageLoader
import com.project.mypokedex.R
import com.project.mypokedex.model.BackgroundType
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.model.SelectableSprite
import com.project.mypokedex.model.SpriteGender
import com.project.mypokedex.model.SpriteTypes
import com.project.mypokedex.model.SpriteVariant
import com.project.mypokedex.sampledata.bulbasaur
import com.project.mypokedex.sampledata.charizard
import com.project.mypokedex.sampledata.charmander
import com.project.mypokedex.sampledata.squirtle
import com.project.mypokedex.ui.components.CardScreen
import com.project.mypokedex.ui.components.CustomTextField
import com.project.mypokedex.ui.components.PokemonGenerationToUI
import com.project.mypokedex.ui.components.PokemonImage
import com.project.mypokedex.ui.components.PokemonTypeToUI
import com.project.mypokedex.ui.components.ResponsiveText
import com.project.mypokedex.ui.components.RotationalImage
import com.project.mypokedex.ui.components.Section
import com.project.mypokedex.ui.stateholders.DetailsScreenUIState
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainWhite
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.theme.Transparent
import com.project.mypokedex.ui.theme.White

@Composable
fun DetailsUIScreen(state: DetailsScreenUIState) {
    CardScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            state.pokemon?.let { pokemon ->

                BasicDetails(
                    pokemon = pokemon,
                    frontImage = state.pokemonFrontImage,
                    backImage = state.pokemonBackImage
                )

                SpriteTypes(
                    spriteGenderOptions = state.spriteGenderOptions,
                    spriteVariantOptions = state.spriteVariantOptions,
                    onSpriteTypeClick = state.onSpriteTypeClick
                )

                Sprites(
                    spriteOptions = state.spriteOptions,
                    onSpriteOptionClick = state.onSpriteOptionClick,
                )

                EvolutionChain(
                    evolutionChain = state.evolutionChain,
                    onPokemonClick = state.onPokemonClick
                )

                Varieties(
                    varieties = state.varieties,
                    onPokemonClick = state.onPokemonClick
                )

                AdvancedDetails(pokemon = pokemon, personHeight = 1.84F * 100)
            }
        }
    }
}

@Composable
fun InputHeightDialog() {
    Dialog(onDismissRequest = { }) {
        Column(
            modifier = Modifier
                .widthIn(max = 250.dp)
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(4.dp))
                .background(MainWhite, RoundedCornerShape(10.dp))
                .border(2.dp, MainBlack, RoundedCornerShape(10.dp)),
            horizontalAlignment = CenterHorizontally
        ) {
            Text(
                text = "Input your height.",
                color = BlackTextColor,
                fontSize = 12.sp,
                style = PokemonGB,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            )

            Text(
                text = "The value must be in centimeter.",
                color = BlackTextColor,
                fontSize = 8.sp,
                style = PokemonGB,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 8.dp)
            )

            var text by rememberSaveable { mutableStateOf("") }
            CustomTextField(
                text = text,
                onValueChange = {
                    text = it
                },
                placeholderText = "Height",
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 10.dp)
                    .widthIn(max = 100.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }
    }
}

@Composable
fun AdvancedDetails(pokemon: Pokemon, personHeight: Float) {
    val imageBaseHeight = 100.dp
    val pokemonHeight = pokemon.height * 10

    var personHeightImage: Dp = imageBaseHeight
    var pokemonHeightImage: Dp = imageBaseHeight

    if (personHeight > pokemonHeight) {
        pokemonHeightImage = ((pokemonHeight * imageBaseHeight.value) / personHeight).dp
    } else {
        personHeightImage = ((personHeight * imageBaseHeight.value) / pokemonHeight).dp
    }

    Section(title = R.string.details_screen_advanced_details_title) {
        Row(
            modifier = Modifier
                .height(imageBaseHeight)
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(imageBaseHeight)
                    .weight(1F)
            ) {
                ResponsiveText(
                    text = stringResource(id = R.string.details_screen_height_title),
                    color = BlackTextColor,
                    targetTextSizeHeight = 12.sp,
                    textStyle = PokemonGB,
                    modifier = Modifier
                        .align(TopCenter)
                )
            }

            Box(
                modifier = Modifier
                    .height(personHeightImage)
                    .weight(1F)
            ) {
                ResponsiveText(
                    text = "$personHeight cm",
                    color = BlackTextColor,
                    targetTextSizeHeight = 10.sp,
                    textStyle = PokemonGB,
                    modifier = Modifier
                        .align(Center)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_height),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MainBlack),
                modifier = Modifier
                    .height(personHeightImage)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MainBlack),
                modifier = Modifier
                    .height(personHeightImage)
            )

            SubcomposeAsyncImage(
                model = pokemon.sprites.other.dream_world.front_default,
                contentDescription = null,
                //colorFilter = ColorFilter.tint(MainBlack),
                modifier = Modifier
                    .height(pokemonHeightImage)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_height),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MainBlack),
                modifier = Modifier
                    .height(pokemonHeightImage)
            )

            Box(
                modifier = Modifier
                    .height(pokemonHeightImage)
                    .weight(1F)
            ) {
                ResponsiveText(
                    text = "$pokemonHeight cm",
                    color = BlackTextColor,
                    targetTextSizeHeight = 10.sp,
                    textStyle = PokemonGB,
                    modifier = Modifier
                        .align(Center)
                )
            }
        }
    }
}

@Composable
fun SpriteTypes(
    spriteGenderOptions: SpriteGender?,
    spriteVariantOptions: SpriteVariant?,
    onSpriteTypeClick: (SpriteTypes) -> Unit
) {
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
                    .shadow(4.dp, RoundedCornerShape(4.dp))
                    .background(MainWhite, RoundedCornerShape(4.dp))
                    .border(1.dp, MainBlack, RoundedCornerShape(4.dp))
                    .clickable {
                        onSpriteTypeClick(it as SpriteTypes)
                    }
                    .padding(vertical = 6.dp, horizontal = 10.dp)
            )
        }
        spriteVariantOptions?.let {
            Text(
                text = spriteVariantOptions.toString(),
                color = BlackTextColor,
                fontSize = 12.sp,
                style = PokemonGB,
                modifier = Modifier
                    .shadow(4.dp, RoundedCornerShape(4.dp))
                    .background(MainWhite, RoundedCornerShape(4.dp))
                    .border(1.dp, MainBlack, RoundedCornerShape(4.dp))
                    .clickable {
                        onSpriteTypeClick(it as SpriteTypes)
                    }
                    .padding(vertical = 6.dp, horizontal = 10.dp)
            )
        }
    }
}

@Composable
fun Sprites(
    spriteOptions: List<Pair<SelectableSprite, String>>,
    onSpriteOptionClick: (SelectableSprite) -> Unit
) {
    Section(title = R.string.details_screen_sprites_title) {
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(spriteOptions) {
                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .clickable {
                            onSpriteOptionClick(it.first)
                        },
                    horizontalAlignment = CenterHorizontally
                ) {
                    SubcomposeAsyncImage(
                        model = it.second,
                        contentDescription = null,
                        imageLoader = LocalContext.current.imageLoader,
                        filterQuality = FilterQuality.High,
                        modifier = Modifier
                            .aspectRatio(1F)
                    )

                    ResponsiveText(
                        text = stringResource(id = it.first.getName()),
                        color = BlackTextColor,
                        targetTextSizeHeight = 14.sp,
                        textStyle = PokemonGB,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Varieties(
    varieties: List<List<Pokemon>>,
    onPokemonClick: (Pokemon) -> Unit
) {
    if (varieties.isNotEmpty()) {
        Section(R.string.details_screen_varieties_title) {
            varieties.forEach { row ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    row.forEach { pokemon ->
                        Box(
                            modifier = Modifier
                                .weight(1F)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp),
                                horizontalAlignment = CenterHorizontally
                            ) {
                                PokemonImage(
                                    url = pokemon.getGifOrImage(),
                                    backgroundType = BackgroundType.None,
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
    }
}

@Composable
fun EvolutionChain(
    evolutionChain: List<List<Pokemon>>,
    onPokemonClick: (Pokemon) -> Unit
) {
    if (evolutionChain.isEmpty()) return

    Section(title = R.string.details_screen_evolution_chain_title) {
        Column {
            evolutionChain.forEach { rowEvolution ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    rowEvolution.forEach { pokemon ->
                        Box(
                            modifier = Modifier
                                .weight(1F)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = CenterHorizontally
                            ) {
                                PokemonImage(
                                    url = pokemon.getGifOrImage(),
                                    backgroundType = BackgroundType.None,
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

        }

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
    }
}

@Composable
fun BasicDetails(pokemon: Pokemon, frontImage: String, backImage: String) {
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

@Preview
@Composable
fun DetailsUIScreenPreview() {
    MyPokedexTheme {
        Surface {
            DetailsUIScreen(
                state = DetailsScreenUIState(
                    pokemon = charizard,
                    evolutionChain = listOf(listOf(bulbasaur, squirtle, charmander)),
                    spriteOptions = listOf(),
                    spriteGenderOptions = SpriteTypes.Male,
                    spriteVariantOptions = SpriteTypes.Normal,
                    varieties = listOf(listOf(bulbasaur, squirtle, charmander))
                )
            )
        }
    }
}