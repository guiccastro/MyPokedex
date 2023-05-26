package com.project.mypokedex.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.PokedexViewModel
import com.project.mypokedex.R
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.Green
import com.project.mypokedex.ui.theme.HomeScreenBackground
import com.project.mypokedex.ui.theme.HomeScreenCard
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import java.text.DecimalFormat

@Composable
fun SinglePokemonScreen(viewModel: PokedexViewModel) {
    Background()

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        BackScreenCard(viewModel)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchPokemon(viewModel)
            DirectionalButtons(viewModel)
        }
    }

}

@Composable
fun Background() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = HomeScreenBackground
    ) {
    }
}

@Composable
fun BackScreenCard(viewModel: PokedexViewModel) {
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
            Screen(viewModel)
        }
    }
}

@Composable
fun Screen(viewModel: PokedexViewModel) {
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

        viewModel.currentPokemonInfo?.let {
            PokemonSingleCard(pokemon = it)
        }
    }
}

@Composable
fun DirectionalButtons(viewModel: PokedexViewModel) {
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
                onClick = { viewModel.onClickUp() },
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
                onClick = { viewModel.onClickPrevious() },
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
                onClick = { viewModel.onClickNext() },
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
                onClick = { viewModel.onClickDown() },
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
fun SearchPokemon(viewModel: PokedexViewModel) {
    var searchValue by remember { mutableStateOf("") }
    val formatter = remember { DecimalFormat("#") }

    OutlinedTextField(
        modifier = Modifier
            .width(220.dp)
            .height(46.dp)
            .background(Green, RoundedCornerShape(25))
            .border(1.dp, BorderBlack, RoundedCornerShape(25)),
        value = searchValue,
        onValueChange = {
            try {
                searchValue = formatter.format(it.toInt())
            } catch (e: IllegalArgumentException) {
                if (it.isEmpty()) {
                    searchValue = it
                }
            }
            viewModel.searchPokemonById(searchValue.toIntOrNull())
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
    val viewModel = PokedexViewModel()
    MyPokedexTheme {
        Surface {
            SinglePokemonScreen(viewModel)
        }
    }
}