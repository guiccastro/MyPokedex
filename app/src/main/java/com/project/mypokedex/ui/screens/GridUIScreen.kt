package com.project.mypokedex.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.imageLoader
import com.project.mypokedex.R
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.sampledata.charizard
import com.project.mypokedex.sampledata.listPokemons
import com.project.mypokedex.ui.components.PokemonTypeToUI
import com.project.mypokedex.ui.components.innerShadow
import com.project.mypokedex.ui.stateholders.GridScreenUIState
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.BorderBlackShadow
import com.project.mypokedex.ui.theme.Green
import com.project.mypokedex.ui.theme.HomeScreenCard
import com.project.mypokedex.ui.theme.MainBlue
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.theme.White
import com.project.mypokedex.ui.viewmodels.GridScreenViewModel

@Composable
fun GridUIScreen(viewModel: GridScreenViewModel, onClick: (Pokemon) -> Unit = {}) {
    val state = viewModel.uiState.collectAsState().value
    GridUIScreen(state = state, onClick = onClick)
}

@Composable
fun GridUIScreen(state: GridScreenUIState, onClick: (Pokemon) -> Unit = {}) {
    Column {
        AnimatedVisibility(
            visible = state.isSearching,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            SearchInputText(
                state = state
            )
        }

        val externalCorner = 8.dp
        val internalCorner = 6.dp
        val externalShape = RoundedCornerShape(externalCorner)
        val internalShape = RoundedCornerShape(internalCorner)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 20.dp, top = 10.dp),
            color = HomeScreenCard,
            shape = externalShape
        ) {
            Surface(
                modifier = Modifier
                    .padding(all = 10.dp)
                    .border((0.5).dp, BorderBlack, internalShape)
                    .innerShadow(
                        color = Color.Black,
                        cornersRadius = internalCorner,
                        blur = 5.dp
                    ),
                color = MainBlue,
                shape = internalShape
            ) {
                AnimatedVisibility(
                    visible = state.showList,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 6.dp)
                    ) {
                        items(state.pokemonList) { pokemon ->
                            PokemonGridCard(pokemon = pokemon, onClick = onClick)
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun PokemonGridCard(pokemon: Pokemon, onClick: (Pokemon) -> Unit = {}) {
    Card(
        modifier = Modifier
            .width(130.dp)
            .height(150.dp)
            .clickable {
                onClick(pokemon)
            },
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {

        Column(
            modifier = Modifier
                .padding(4.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = pokemon.formattedID(),
                fontSize = 8.sp,
                fontWeight = FontWeight(400),
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                style = PokemonGB
            )
            Text(
                text = pokemon.formattedName(),
                fontSize = 10.sp,
                fontWeight = FontWeight(800),
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                style = PokemonGB,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                pokemon.types.forEach {
                    PokemonTypeToUI(pokemonType = it)
                }
            }

            SubcomposeAsyncImage(
                model = pokemon.getGifOrImage(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            listOf(
                                White.copy(alpha = 0.5F),
                                Color.Transparent
                            )
                        )
                    ),
                imageLoader = LocalContext.current.imageLoader,
                filterQuality = FilterQuality.High
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(40.dp),
                            color = BorderBlack
                        )
                    }

                    is AsyncImagePainter.State.Error -> {
                        Image(
                            modifier = Modifier
                                .padding(40.dp),
                            painter = painterResource(id = R.drawable.ic_error),
                            contentDescription = "Error",
                            colorFilter = ColorFilter.tint(BorderBlack)
                        )
                    }

                    else -> {
                        SubcomposeAsyncImageContent()

                    }
                }
            }
        }

    }
}

@Composable
fun SearchInputText(state: GridScreenUIState) {
    val shape = RoundedCornerShape(4.dp)
    OutlinedTextField(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
            .fillMaxWidth()
            .background(Green, shape)
            .border(1.dp, BorderBlack, shape),
        value = state.searchText,
        onValueChange = {
            state.onSearchChange(it)
        },
        shape = shape,
        leadingIcon = {
            Image(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                colorFilter = ColorFilter.tint(BorderBlack)
            )
        },
        placeholder = {
            Text(
                text = "Search",
                style = PokemonGB,
                fontSize = 12.sp,
                maxLines = 1
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Green,
            unfocusedContainerColor = Green,
            disabledContainerColor = Green,
            errorContainerColor = Green,
            focusedTextColor = BorderBlack,
            disabledTextColor = BorderBlack,
            errorTextColor = BorderBlack,
            unfocusedTextColor = BorderBlack,
            focusedIndicatorColor = BorderBlack,
            focusedLeadingIconColor = BorderBlack,
            cursorColor = BorderBlack,
            selectionColors = TextSelectionColors(BorderBlack, BorderBlackShadow)
        ),
        textStyle = PokemonGB.copy(fontSize = 12.sp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Password
        )
    )
}

@Preview
@Composable
fun GridUIScreenPreview() {
    MyPokedexTheme {
        Surface {
            GridUIScreen(
                GridScreenUIState(
                    pokemonList = listPokemons
                )
            )
        }
    }
}

@Preview
@Composable
fun PokemonGridCardPreview() {
    MyPokedexTheme {
        Surface {
            PokemonGridCard(charizard)
        }
    }
}