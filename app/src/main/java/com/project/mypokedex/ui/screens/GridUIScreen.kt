package com.project.mypokedex.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.model.BackgroundType
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.sampledata.charizard
import com.project.mypokedex.sampledata.listPokemons
import com.project.mypokedex.ui.CardScreen
import com.project.mypokedex.ui.components.AppIcon
import com.project.mypokedex.ui.components.PokemonImage
import com.project.mypokedex.ui.components.PokemonTypeToUI
import com.project.mypokedex.ui.stateholders.GridScreenUIState
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainSelectionTextBackground
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.theme.SearchTextBackground
import com.project.mypokedex.ui.theme.Transparent
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

        CardScreen(
            cardPadding = PaddingValues(start = 10.dp, end = 10.dp, bottom = 20.dp, top = 10.dp)
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

@Composable
fun PokemonGridCard(pokemon: Pokemon, onClick: (Pokemon) -> Unit = {}) {
    Column(
        modifier = Modifier
            .width(130.dp)
            .height(150.dp)
            .padding(4.dp)
            .background(Transparent, RoundedCornerShape(4.dp))
            .clickable {
                onClick(pokemon)
            },
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // Pokemon ID
        Text(
            text = pokemon.formattedID(),
            fontSize = 8.sp,
            fontWeight = FontWeight(400),
            color = BlackTextColor,
            textAlign = TextAlign.Center,
            style = PokemonGB
        )

        // Pokemon Name
        Text(
            text = pokemon.formattedName(),
            fontSize = 10.sp,
            fontWeight = FontWeight(800),
            color = BlackTextColor,
            textAlign = TextAlign.Center,
            style = PokemonGB,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // Pokemon Types
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

        // Pokemon Image
        PokemonImage(
            url = pokemon.getGifOrImage(),
            modifier = Modifier
                .fillMaxSize(),
            clickable = null,
            backgroundType = BackgroundType.ImageBackground(
                id = R.drawable.ic_pokeball,
                color1 = pokemon.types.getOrNull(0)?.getColor(),
                color2 = pokemon.types.getOrNull(1)?.getColor()
            )
        )
    }

}

@Composable
fun SearchInputText(state: GridScreenUIState) {
    val shape = RoundedCornerShape(4.dp)
    OutlinedTextField(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
            .fillMaxWidth()
            .background(SearchTextBackground, shape),
        value = state.searchText,
        onValueChange = {
            state.onSearchChange(it)
        },
        shape = shape,
        leadingIcon = {
            AppIcon(
                id = R.drawable.ic_search
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.grid_screen_search),
                style = PokemonGB,
                fontSize = 12.sp,
                maxLines = 1
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = SearchTextBackground,
            unfocusedContainerColor = SearchTextBackground,
            disabledContainerColor = SearchTextBackground,
            errorContainerColor = SearchTextBackground,
            focusedTextColor = BlackTextColor,
            disabledTextColor = BlackTextColor,
            errorTextColor = BlackTextColor,
            unfocusedTextColor = BlackTextColor,
            focusedIndicatorColor = MainBlack,
            focusedLeadingIconColor = MainBlack,
            cursorColor = MainBlack,
            selectionColors = TextSelectionColors(MainBlack, MainSelectionTextBackground)
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