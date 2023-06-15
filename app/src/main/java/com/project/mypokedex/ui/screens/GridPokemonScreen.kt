package com.project.mypokedex.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.project.mypokedex.sampledata.listPokemons
import com.project.mypokedex.ui.components.PokemonGridCard
import com.project.mypokedex.ui.components.customShadow
import com.project.mypokedex.ui.components.innerShadow
import com.project.mypokedex.ui.stateholders.GridPokemonScreenStateHolder
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.BorderBlackShadow
import com.project.mypokedex.ui.theme.Green
import com.project.mypokedex.ui.theme.HeavyRed
import com.project.mypokedex.ui.theme.HomeScreenCard
import com.project.mypokedex.ui.theme.MainBlue
import com.project.mypokedex.ui.theme.MainRed
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.viewmodels.GridPokemonScreenViewModel


@Composable
fun GridPokemonScreen(viewModel: GridPokemonScreenViewModel) {
    val state = viewModel.uiState.collectAsState().value
    GridPokemonScreen(state = state)
}

@Composable
fun GridPokemonScreen(state: GridPokemonScreenStateHolder = GridPokemonScreenStateHolder()) {
    Screen(state = state)
}

@Composable
fun AnimatedEnter(state: GridPokemonScreenStateHolder) {
    val animVisibleState = remember { MutableTransitionState(state.isDownloading) }.apply {
        targetState = state.isDownloading
    }
    if (!animVisibleState.targetState && !animVisibleState.currentState) {
        rememberSystemUiController().setSystemBarsColor(MainRed)
        rememberSystemUiController().setNavigationBarColor(MainRed)
    } else {
        rememberSystemUiController().setSystemBarsColor(HeavyRed)
        rememberSystemUiController().setNavigationBarColor(Color.White)
    }

    Column {
        AnimatedVisibility(
            visibleState = animVisibleState,
            exit = slideOutVertically(
                animationSpec = tween(2000, delayMillis = 1000),
                targetOffsetY = { -it }
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .background(HeavyRed)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .background(BorderBlack, RectangleShape)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                verticalArrangement = Arrangement.Bottom
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .offset(y = 100.dp)
                            .background(Color.White, CircleShape)
                            .border(20.dp, BorderBlack, CircleShape)
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = state.isDownloading,
            exit = slideOutVertically(
                animationSpec = tween(2000, delayMillis = 1000),
                targetOffsetY = { it },
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(BorderBlack, RectangleShape)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clipToBounds(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .offset(y = (-100).dp)
                        .background(Color.White, CircleShape)
                        .border(20.dp, BorderBlack, CircleShape)
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if (state.isDownloading) {
                CircularProgressIndicator(
                    progress = state.downloadProgress,
                    modifier = Modifier
                        .size(100.dp),
                    color = BorderBlack,
                    strokeWidth = 5.dp
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if (state.isDownloading) {
                Text(
                    text = state.formattedDownloadProgress,
                    color = BorderBlack
                )
            }
        }
    }
}

@Composable
fun Screen(state: GridPokemonScreenStateHolder) {
    Column {
        AnimatedVisibility(
            visible = state.isSearching,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            SearchPokemon(
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
                .padding(start = 10.dp, end = 10.dp, bottom = 20.dp, top = 10.dp)
                .border((0.3).dp, BorderBlackShadow, externalShape)
                .customShadow(
                    color = Color.Black.copy(alpha = 0.7f),
                    blurRadius = 5.dp,
                    borderRadius = externalCorner
                ),
            color = HomeScreenCard,
            shape = externalShape
        ) {
            Surface(
                modifier = Modifier
                    .padding(all = 10.dp)
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
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 6.dp)
                    ) {
                        items(state.pokemonList) { pokemon ->
                            PokemonGridCard(pokemon = pokemon)
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun SearchPokemon(state: GridPokemonScreenStateHolder) {
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
fun GridPokemonScreenPreview() {
    MyPokedexTheme {
        Surface {
            GridPokemonScreen(
                GridPokemonScreenStateHolder(
                    pokemonList = listPokemons,
                    downloadProgress = 1F,
                    formattedDownloadProgress = "100.00%",
                    isDownloading = false
                )
            )
        }
    }
}