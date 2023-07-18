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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.project.mypokedex.ui.components.PokemonImage
import com.project.mypokedex.ui.components.PokemonTypeToUI
import com.project.mypokedex.ui.stateholders.ListScreenUIState
import com.project.mypokedex.ui.theme.CardColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.theme.SearchTextBackground
import com.project.mypokedex.ui.viewmodels.ListScreenViewModel

@Composable
fun ListUIScreen(viewModel: ListScreenViewModel) {
    val state = viewModel.uiState.collectAsState().value
    ListUIScreen(state = state)
}

@Composable
fun ListUIScreen(state: ListScreenUIState = ListScreenUIState()) {
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
            SearchInputText(state)
            DirectionalButtons(state)
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Screen(state: ListScreenUIState) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(24.dp),
        color = CardColor,
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
                border = BorderStroke(1.dp, MainBlack),
                shadowElevation = 5.dp
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_screen_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )

                HorizontalPager(
                    state = rememberPagerState { state.pokemonList.size },
                    pageSpacing = 10.dp
                ) {
                    PokemonListCard(pokemon = state.pokemonList[it])
                }
            }
        }
    }

}

@Composable
fun DirectionalButtons(state: ListScreenUIState) {
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
                border = BorderStroke(1.dp, MainBlack),
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
                border = BorderStroke(1.dp, MainBlack),
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
                border = BorderStroke(1.dp, MainBlack),
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
                border = BorderStroke(1.dp, MainBlack),
                colors = ButtonDefaults.buttonColors(MainBlack)
            ) {
            }
        }
    }
}

@Composable
fun PokemonListCard(pokemon: Pokemon) {
    Row(modifier = Modifier.padding(horizontal = 6.dp)) {
        Box(modifier = Modifier.size(150.dp)) {
            PokemonImage(
                url = pokemon.getGifOrImage(),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(4.dp),
                clickable = null,
                backgroundType = BackgroundType.None
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
                    color = MainBlack,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = PokemonGB
                )
                Text(
                    text = pokemon.formattedName(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = MainBlack,
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
fun SearchInputText(state: ListScreenUIState) {
    OutlinedTextField(
        modifier = Modifier
            .width(220.dp)
            .height(46.dp)
            .background(SearchTextBackground, RoundedCornerShape(25))
            .border(1.dp, MainBlack, RoundedCornerShape(25)),
        value = state.searchText,
        onValueChange = {
            state.onSearchChange(it)
        },
        shape = RoundedCornerShape(25),
        leadingIcon = {
            Image(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MainBlack)
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
            focusedContainerColor = SearchTextBackground,
            unfocusedContainerColor = SearchTextBackground,
            disabledContainerColor = SearchTextBackground,
            errorContainerColor = SearchTextBackground,
            focusedTextColor = MainBlack,
            disabledTextColor = MainBlack,
            errorTextColor = MainBlack,
            unfocusedTextColor = MainBlack
        ),
        textStyle = PokemonGB.copy(fontSize = 12.sp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )

}

@Preview
@Composable
fun ListUiScreenPreview() {
    MyPokedexTheme {
        Surface {
            ListUIScreen()
        }
    }
}

@Preview
@Composable
fun PokemonListCardPreview() {
    PokemonListCard(charizard)
}