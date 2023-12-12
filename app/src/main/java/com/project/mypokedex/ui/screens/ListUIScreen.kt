package com.project.mypokedex.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.model.BackgroundType
import com.project.mypokedex.model.Pokemon
import com.project.mypokedex.sampledata.charizard
import com.project.mypokedex.ui.components.CardScreen
import com.project.mypokedex.ui.components.PokemonImage
import com.project.mypokedex.ui.components.PokemonTypeToUI
import com.project.mypokedex.ui.components.SearchInputText
import com.project.mypokedex.ui.stateholders.ListScreenUIState
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.theme.White
import com.project.mypokedex.ui.viewmodels.ListScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlin.math.min

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListUIScreen(viewModel: ListScreenViewModel) {
    val state = viewModel.uiState.collectAsState().value
    val pagerState = rememberPagerState { state.pokemonList.size }
    ListUIScreen(state = state, pagerState = pagerState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListUIScreen(
    state: ListScreenUIState = ListScreenUIState(),
    pagerState: PagerState = rememberPagerState { 1 }
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Screen(state, pagerState)
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SearchInputText(
                    searchText = state.searchText,
                    onSearchChange = {
                        CoroutineScope(Main).launch {
                            pagerState.scrollToPage(0)
                        }
                        state.onSearchChange(it)
                    }
                )
                DirectionalButtons(state, pagerState)
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Screen(state: ListScreenUIState, pagerState: PagerState) {
    CardScreen(
        cardModifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        screenModifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        cardCorner = 24.dp,
        screenCorner = 16.dp,
        cardBorderSize = PaddingValues(vertical = 40.dp, horizontal = 20.dp),
        cardPadding = PaddingValues(horizontal = 20.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_screen_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        HorizontalPager(
            state = pagerState,
            pageSpacing = 10.dp
        ) {
            PokemonListCard(pokemon = state.pokemonList[it], onClick = state.onPokemonClick)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DirectionalButtons(state: ListScreenUIState, pagerState: PagerState) {
    val directionalsSize = 160.dp
    val buttonWidth = (directionalsSize.value / 3.44).dp
    val buttonHeight = (directionalsSize.value / 2.81).dp
    val animationScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .size(directionalsSize)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            /** UP BUTTON **/
            Button(
                onClick = {
                    animationScope.launch {
                        pagerState.animateScrollToPage(
                            min(state.pokemonList.size - 11, pagerState.currentPage) + 10
                        )
                    }
                },
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
                onClick = { animationScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } },
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
                onClick = { animationScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } },
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
                onClick = {
                    animationScope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.currentPage.coerceAtLeast(10) - 10
                        )
                    }
                },
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
fun PokemonListCard(pokemon: Pokemon, onClick: (Pokemon) -> Unit = {}) {
    Row(
        modifier = Modifier
            .padding(horizontal = 6.dp)
            .clickable {
                onClick(pokemon)
            }
    ) {
        Box(modifier = Modifier.size(150.dp)) {
            PokemonImage(
                url = pokemon.getGifOrImage(),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(4.dp),
                clickable = null,
                backgroundType = BackgroundType.ImageBackground(
                    id = R.drawable.ic_pokeball,
                    color1 = White.copy(alpha = 0.5f)
                )
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
                    PokemonTypeToUI(pokemonType = it, size = 34.dp)
                }
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun ListUiScreenPreview() {
    MyPokedexTheme {
        Surface {
            ListUIScreen(
                state = ListScreenUIState(
                    pokemonList = listOf(charizard)
                )
            )
        }
    }
}

@Preview
@Composable
fun PokemonListCardPreview() {
    PokemonListCard(charizard)
}