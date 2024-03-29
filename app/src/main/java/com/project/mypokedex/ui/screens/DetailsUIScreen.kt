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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
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
import com.project.mypokedex.ui.components.AppButton
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
import com.project.mypokedex.ui.theme.MainBlue
import com.project.mypokedex.ui.theme.MainRed
import com.project.mypokedex.ui.theme.MainWhite
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.theme.Transparent
import com.project.mypokedex.ui.theme.White

@Composable
fun DetailsUIScreen(state: DetailsScreenUIState) {
    CardScreen(
        cardInternBackground = state.pokemon?.species?.color?.getColor() ?: MainBlue
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp)
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

                AdvancedDetails(
                    pokemon = pokemon,
                    personHeight = state.personHeight,
                    heightDialogState = state.heightDialogState,
                    onChangeHeightDialogState = state.onChangeHeightDialogState,
                    onSaveHeightDialog = state.onSaveHeightDialog,
                    verifyNewHeightText = state.verifyNewHeightText,
                    heightDialogStateError = state.heightDialogStateError
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun BasicDetails(
    pokemon: Pokemon,
    frontImage: String,
    backImage: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 10.dp),
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

@Composable
fun SpriteTypes(
    spriteGenderOptions: SpriteGender?,
    spriteVariantOptions: SpriteVariant?,
    onSpriteTypeClick: (SpriteTypes) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        spriteGenderOptions?.let {
            Text(
                text = stringResource(id = (spriteGenderOptions as SpriteTypes).getStringRes()),
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
                text = stringResource(id = (spriteVariantOptions as SpriteTypes).getStringRes()),
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier)
            }

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

            item {
                Spacer(modifier = Modifier)
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
        Column(
            modifier = Modifier
                .padding(4.dp)
        ) {
            evolutionChain.forEach { rowEvolution ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 10.dp)
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
                                    },
                                    modifier = Modifier
                                        .heightIn(max = 100.dp)
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
    }
}

@Composable
fun Varieties(
    varieties: List<List<Pokemon>>,
    onPokemonClick: (Pokemon) -> Unit
) {
    if (varieties.isNotEmpty()) {
        Section(R.string.details_screen_varieties_title) {
            Column {
                varieties.forEach { row ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 10.dp)
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
                                        },
                                        modifier = Modifier
                                            .heightIn(max = 100.dp)
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
}

@Composable
fun AdvancedDetails(
    pokemon: Pokemon,
    personHeight: Int,
    heightDialogState: Boolean,
    onChangeHeightDialogState: (Boolean) -> Unit,
    onSaveHeightDialog: (String) -> Unit,
    verifyNewHeightText: (String) -> Unit,
    heightDialogStateError: Boolean
) {
    Section(title = R.string.details_screen_advanced_details_title) {
        Column {
            HeightInfo(
                pokemon = pokemon,
                personHeight = personHeight,
                heightDialogState = heightDialogState,
                onChangeHeightDialogState = onChangeHeightDialogState,
                onSaveHeightDialog = onSaveHeightDialog,
                verifyNewHeightText = verifyNewHeightText,
                heightDialogStateError = heightDialogStateError
            )

            Divider(
                thickness = 1.dp,
                color = MainBlack,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )

            WeightInfo(
                pokemon = pokemon
            )
        }
    }
}

@Composable
fun HeightInfo(
    pokemon: Pokemon,
    personHeight: Int,
    heightDialogState: Boolean,
    onChangeHeightDialogState: (Boolean) -> Unit,
    onSaveHeightDialog: (String) -> Unit,
    verifyNewHeightText: (String) -> Unit,
    heightDialogStateError: Boolean
) {
    val imageBaseHeight = 100.dp
    val pokemonHeight = pokemon.height * 10

    var personHeightImage: Dp = imageBaseHeight
    var pokemonHeightImage: Dp = imageBaseHeight

    if (personHeight > pokemonHeight) {
        pokemonHeightImage = ((pokemonHeight * imageBaseHeight.value) / personHeight).dp
    } else {
        personHeightImage = ((personHeight * imageBaseHeight.value) / pokemonHeight).dp
    }
    Row(
        modifier = Modifier
            .height(imageBaseHeight)
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = 2.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            Column(
                modifier = Modifier
                    .heightIn(min = 40.dp)
                    .height(personHeightImage),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$personHeight cm",
                    color = BlackTextColor,
                    fontSize = 10.sp,
                    style = PokemonGB
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MainBlack),
                    modifier = Modifier
                        .padding(4.dp)
                        .size(16.dp)
                        .border(1.dp, MainBlack, RoundedCornerShape(4.dp))
                        .background(MainWhite, RoundedCornerShape(4.dp))
                        .padding(2.dp)
                        .clickable {
                            onChangeHeightDialogState(true)
                        },
                )

                if (heightDialogState) {
                    InputHeightDialog(
                        onChangeHeightDialogState,
                        onSaveHeightDialog,
                        verifyNewHeightText,
                        heightDialogStateError
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ic_height),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MainBlack),
                modifier = Modifier
                    .height(personHeightImage)
                    .widthIn(max = 10.dp),
                contentScale = ContentScale.FillBounds
            )

            Image(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MainBlack),
                modifier = Modifier
                    .height(personHeightImage)
            )
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                model = pokemon.getImageForHeightInfo(),
                contentDescription = null,
                modifier = Modifier
                    .height(pokemonHeightImage),
            )

            Image(
                painter = painterResource(id = R.drawable.ic_height),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MainBlack),
                modifier = Modifier
                    .height(pokemonHeightImage)
                    .widthIn(max = 10.dp),
                contentScale = ContentScale.FillBounds
            )

            Box(
                modifier = Modifier
                    .heightIn(min = 10.dp)
                    .height(pokemonHeightImage),
            ) {
                Text(
                    text = "$pokemonHeight cm",
                    color = BlackTextColor,
                    fontSize = 10.sp,
                    style = PokemonGB,
                    modifier = Modifier
                        .align(Center)
                )
            }
        }
    }
}

@Composable
fun InputHeightDialog(
    onChangeHeightDialogState: (Boolean) -> Unit,
    onSaveHeightDialog: (String) -> Unit,
    verifyNewHeightText: (String) -> Unit,
    heightDialogStateError: Boolean
) {
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
                text = stringResource(id = R.string.input_height_dialog_title),
                color = BlackTextColor,
                fontSize = 12.sp,
                style = PokemonGB,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            )

            Text(
                text = stringResource(id = R.string.input_height_dialog_desc),
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
                    verifyNewHeightText(it)
                },
                placeholderText = stringResource(id = R.string.input_height_dialog_hint),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 2.dp)
                    .widthIn(max = 100.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            if (heightDialogStateError) {
                Text(
                    text = stringResource(id = R.string.input_height_dialog_error),
                    color = MainRed,
                    fontSize = 6.sp,
                    style = PokemonGB,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(bottom = 2.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 10.dp)
                    .padding(top = 8.dp)
            ) {
                AppButton(
                    text = stringResource(id = R.string.input_height_dialog_cancel),
                    onClick = { onChangeHeightDialogState(false) },
                    modifier = Modifier
                        .weight(1F),
                    containerColor = MainBlack.copy(alpha = 0.3F),
                    borderWidth = 1.dp,
                    textSize = 10.sp
                )

                AppButton(
                    text = stringResource(id = R.string.input_height_dialog_save),
                    onClick = { onSaveHeightDialog(text) },
                    modifier = Modifier
                        .weight(1F),
                    containerColor = White.copy(alpha = 0.3F),
                    borderWidth = 1.dp,
                    textSize = 10.sp,
                    enabled = !heightDialogStateError
                )
            }
        }
    }
}

@Composable
fun WeightInfo(pokemon: Pokemon) {
    val weight = pokemon.weight.toFloat() / 10F
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_weight),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp),
            colorFilter = ColorFilter.tint(MainBlack)
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 4.dp)
        ) {
            Text(
                text = "$weight kg",
                color = BlackTextColor,
                fontSize = 12.sp,
                style = PokemonGB,
                modifier = Modifier
                    .align(Center)
            )
        }
    }
}

@Preview
@Composable
fun WeightInfoPreview() {
    MyPokedexTheme {
        Surface {
            WeightInfo(charizard)
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
                    spriteOptions = listOf(),
                    spriteGenderOptions = SpriteTypes.Male,
                    spriteVariantOptions = SpriteTypes.Normal,
                    varieties = listOf(listOf(bulbasaur, squirtle, charmander))
                )
            )
        }
    }
}