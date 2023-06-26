package com.project.mypokedex.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.imageLoader
import com.project.mypokedex.R
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.sampledata.charizard
import com.project.mypokedex.ui.components.Background
import com.project.mypokedex.ui.components.PokemonTypeToUI
import com.project.mypokedex.ui.stateholders.ListPokemonScreenUIState
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.Green
import com.project.mypokedex.ui.theme.HomeScreenCard
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.viewmodels.ListPokemonScreenViewModel

@Composable
fun ListPokemonScreen(viewModel: ListPokemonScreenViewModel) {
    val state = viewModel.uiState.collectAsState().value
    ListPokemonScreen(state = state)
}

@Composable
fun ListPokemonScreen(state: ListPokemonScreenUIState = ListPokemonScreenUIState()) {
    Background()

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Screen(state)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchPokemon(state)
            DirectionalButtons(state)
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Screen(state: ListPokemonScreenUIState) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(24.dp),
        color = HomeScreenCard,
        shadowElevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, BorderBlack),
                shadowElevation = 5.dp
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_screen_background),
                    contentDescription = "Screen Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )

                HorizontalPager(
                    state = rememberPagerState { state.pokemonList.size },
                    pageSpacing = 10.dp
                ) {
                    PokemonSingleCard(pokemon = state.pokemonList[it])
                }
            }
        }
    }

}

@Composable
fun DirectionalButtons(state: ListPokemonScreenUIState) {
    val directionalsSize = 120.dp
    val buttonWidth = (directionalsSize.value / 3.44).dp
    val buttonHeight = (directionalsSize.value / 2.81).dp
    Column(
        modifier = Modifier
            .size(directionalsSize)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            /** UP BUTTON **/
            Button(
                onClick = { state.onClickUp() },
                modifier = Modifier
                    .width(buttonWidth)
                    .height(buttonHeight),
                shape = RoundedCornerShape(
                    topStart = 25.dp,
                    topEnd = 25.dp,
                    bottomStart = 5.dp,
                    bottomEnd = 5.dp
                ),
                border = BorderStroke(1.dp, BorderBlack),
                colors = ButtonDefaults.buttonColors(MainBlack)
            ) {
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            /** LEFT BUTTON **/
            Button(
                onClick = { state.onClickLeft() },
                modifier = Modifier
                    .width(buttonHeight)
                    .height(buttonWidth),
                shape = RoundedCornerShape(
                    topStart = 25.dp,
                    topEnd = 5.dp,
                    bottomStart = 25.dp,
                    bottomEnd = 5.dp
                ),
                border = BorderStroke(1.dp, BorderBlack),
                colors = ButtonDefaults.buttonColors(MainBlack)
            ) {
            }

            /** RIGHT BUTTON **/
            Button(
                onClick = { state.onClickRight() },
                modifier = Modifier
                    .width(buttonHeight)
                    .height(buttonWidth),
                shape = RoundedCornerShape(
                    topStart = 5.dp,
                    topEnd = 25.dp,
                    bottomStart = 5.dp,
                    bottomEnd = 25.dp
                ),
                border = BorderStroke(1.dp, BorderBlack),
                colors = ButtonDefaults.buttonColors(MainBlack)
            ) {
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            /** DOWN BUTTON **/
            Button(
                onClick = { state.onClickDown() },
                modifier = Modifier
                    .width(buttonWidth)
                    .height(buttonHeight),
                shape = RoundedCornerShape(
                    topStart = 5.dp,
                    topEnd = 5.dp,
                    bottomStart = 25.dp,
                    bottomEnd = 25.dp
                ),
                border = BorderStroke(1.dp, BorderBlack),
                colors = ButtonDefaults.buttonColors(MainBlack)
            ) {
            }
        }
    }
}

@Composable
fun PokemonSingleCard(pokemon: Pokemon) {
    Row(modifier = Modifier.padding(horizontal = 6.dp)) {
        Box(modifier = Modifier.size(150.dp)) {
            AsyncImage(
                model = pokemon.gif,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(4.dp),
                imageLoader = LocalContext.current.imageLoader,
                filterQuality = FilterQuality.High
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Column {
                Text(
                    text = pokemon.formattedID(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = Color.DarkGray,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = PokemonGB
                )
                Text(
                    text = pokemon.formattedName(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color.DarkGray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = PokemonGB,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items(pokemon.types) {
                    PokemonTypeToUI(pokemonType = it, fontSize = 12.sp)
                }
            }
        }

    }
}

@Composable
fun SearchPokemon(state: ListPokemonScreenUIState) {
    OutlinedTextField(
        modifier = Modifier
            .width(220.dp)
            .height(46.dp)
            .background(Green, RoundedCornerShape(25))
            .border(1.dp, BorderBlack, RoundedCornerShape(25)),
        value = state.searchText,
        onValueChange = {
            state.onSearchChange(it)
        },
        shape = RoundedCornerShape(25),
        leadingIcon = {
            Image(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                colorFilter = ColorFilter.tint(BorderBlack)
            )
        },
        placeholder = {
            Text(
                text = "Search by ID",
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
            unfocusedTextColor = BorderBlack
        ),
        textStyle = PokemonGB.copy(fontSize = 12.sp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )

}

@Preview
@Composable
fun HomeScreenPreview() {
    MyPokedexTheme {
        Surface {
            ListPokemonScreen()
        }
    }
}

@Preview
@Composable
fun PokemonCardPreview() {
    PokemonSingleCard(charizard)
}