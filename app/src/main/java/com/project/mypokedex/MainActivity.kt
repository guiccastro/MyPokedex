package com.project.mypokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.project.mypokedex.data.Pokemon
import com.project.mypokedex.repository.PokemonRepository
import com.project.mypokedex.ui.theme.MainBlue
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.BorderBlackShadow
import com.project.mypokedex.ui.theme.Green
import com.project.mypokedex.ui.theme.HeavyRed
import com.project.mypokedex.ui.theme.LightGray
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainGray
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.MainRed
import com.project.mypokedex.ui.theme.Transparent
import com.project.mypokedex.ui.theme.Yellow
import com.project.mypokedex.ui.theme.cardShape
import com.project.mypokedex.ui.theme.directionalButtonsShape
import com.project.mypokedex.ui.theme.getImageLoader
import com.project.mypokedex.ui.theme.idShape
import com.project.mypokedex.ui.theme.pokedexScreenShape

class MainActivity : ComponentActivity() {
    private val viewModel: PokedexViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPokedexTheme {
                MainBackground(viewModel)
            }
        }

        observeRepository()
    }

    private fun observeRepository() {
        PokemonRepository.pokemonList.observe(this) {
            viewModel.onListUpdate(it)
        }
    }
}

@Composable
fun MainBackground(viewModel: PokedexViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MainRed
    ) {

    }

    Column {
        PokedexTop()

        Divider(
            color = Transparent,
            thickness = 40.dp
        )

        PokedexScreen(viewModel)
    }

    PokedexTopDetails()

    PokedexBottomDetails(viewModel)
}

@Composable
fun PokedexTop() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
    ) {
        val borderWidth = 16.dp.value
        val shadowWidth = 30.dp.value

        var yBorder = size.height + (borderWidth / 2)
        var yShadow = yBorder + (shadowWidth / 2)
        var yBackground = yBorder - (borderWidth / 2)

        val x = size.width / 3

        val pathBorder = Path()
        val pathShadow = Path()
        val pathBackground = Path()

        pathBackground.moveTo(0F, 0F)
        pathBackground.lineTo(0F, yBackground)
        pathBackground.lineTo(x, yBackground)

        pathBorder.moveTo(0F, yBorder)
        pathShadow.moveTo(0F, yShadow)

        pathBorder.lineTo(x, yBorder)
        pathShadow.lineTo(x, yShadow)

        val yOffset = (size.height / 3) * 2
        yBorder -= yOffset
        yShadow -= yOffset
        yBackground -= yOffset
        pathBorder.lineTo(x * 2, yBorder)
        pathShadow.lineTo(x * 2, yShadow)
        pathBackground.lineTo(x * 2, yBorder - (borderWidth / 2))

        pathBorder.lineTo(size.width, yBorder)
        pathShadow.lineTo(size.width, yShadow)
        pathBackground.lineTo(size.width, yBorder - (borderWidth / 2))
        pathBackground.lineTo(size.width, 0F)
        pathBackground.lineTo(0F, 0F)

        drawPath(
            color = BorderBlack,
            path = pathBorder,
            style = Stroke(
                width = borderWidth
            )
        )

        drawPath(
            color = BorderBlackShadow,
            path = pathShadow,
            style = Stroke(
                width = shadowWidth
            )
        )

        drawPath(
            color = MainRed,
            path = pathBackground
        )
    }
}

@Composable
fun PokedexScreen(viewModel: PokedexViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(20.dp, 0.dp)
            .background(LightGray, pokedexScreenShape)
    ) {
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            val path = Path()
            path.moveTo(0F, size.height - 150.dp.value)
            path.lineTo(0F, size.height)
            path.lineTo(150.dp.value, size.height)
            path.lineTo(0F, size.height - 150.dp.value)

            drawPath(
                path = path,
                color = MainRed,
                blendMode = BlendMode.Src
            )
        }

        Surface(
            modifier = Modifier
                .matchParentSize()
                .padding(30.dp, 30.dp, 30.dp, 80.dp)
                .border(6.dp, BorderBlack, pokedexScreenShape),
            shape = pokedexScreenShape,
            color = MainBlue
        ) {
            //viewModel.pokemonsList?.let { PokemonBaseList(it, viewModel.onClickCard) }
            viewModel.currentPokemonInfo?.let { PokemonBaseCard(it) }
        }

        Surface(
            modifier = Modifier
                .size(40.dp)
                .offset(60.dp, 240.dp),
            shape = CircleShape,
            color = HeavyRed
        ) {

        }
    }
}

@Composable
fun PokedexTopDetails() {
    Box(
        modifier = Modifier
            .padding(10.dp, 10.dp)
    ) {
        Surface(
            modifier = Modifier
                .size(100.dp),
            shape = CircleShape,
            border = BorderStroke(3.dp, BorderBlack),
            color = White
        ) {

        }

        Surface(
            modifier = Modifier
                .size(70.dp)
                .offset(15.dp, 15.dp),
            shape = CircleShape,
            border = BorderStroke(3.dp, BorderBlack),
            color = MainBlue
        ) {

        }

        Surface(
            modifier = Modifier
                .size(30.dp)
                .offset(100.dp, 0.dp),
            shape = CircleShape,
            border = BorderStroke(3.dp, BorderBlack),
            color = HeavyRed
        ) {

        }

        Surface(
            modifier = Modifier
                .size(30.dp)
                .offset(135.dp, 0.dp),
            shape = CircleShape,
            border = BorderStroke(3.dp, BorderBlack),
            color = Yellow
        ) {

        }

        Surface(
            modifier = Modifier
                .size(30.dp)
                .offset(170.dp, 0.dp),
            shape = CircleShape,
            border = BorderStroke(3.dp, BorderBlack),
            color = Green
        ) {

        }
    }
}

@Composable
fun PokedexBottomDetails(viewModel: PokedexViewModel) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .offset(0.dp, 500.dp)
    ) {
        Surface(
            modifier = Modifier
                .size(50.dp)
                .offset(15.dp, (-15).dp),
            shape = CircleShape,
            border = BorderStroke(3.dp, BorderBlack),
            color = MainBlack
        ) {

        }

        Surface(
            modifier = Modifier
                .height(20.dp)
                .width(70.dp)
                .offset(80.dp, 0.dp),
            shape = directionalButtonsShape,
            border = BorderStroke(3.dp, BorderBlack),
            color = HeavyRed
        ) {

        }

        Surface(
            modifier = Modifier
                .height(80.dp)
                .width(200.dp)
                .offset(15.dp, 50.dp),
            shape = directionalButtonsShape,
            border = BorderStroke(3.dp, BorderBlack),
            color = Green
        ) {

        }

        Surface(
            modifier = Modifier
                .height(20.dp)
                .width(70.dp)
                .offset(160.dp, 0.dp),
            shape = directionalButtonsShape,
            border = BorderStroke(3.dp, BorderBlack),
            color = MainBlue
        ) {

        }

        Box(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .offset(240.dp, 0.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(50.dp)
                    .offset(40.dp, 0.dp),
                shape = directionalButtonsShape,
                border = BorderStroke(3.dp, BorderBlack),
                colors = ButtonDefaults.buttonColors(MainBlack)
            ) {

            }

            Button(
                onClick = { viewModel.onClickPrevious() },
                modifier = Modifier
                    .size(50.dp)
                    .offset(0.dp, 40.dp),
                shape = directionalButtonsShape,
                border = BorderStroke(3.dp, BorderBlack),
                colors = ButtonDefaults.buttonColors(MainBlack)
            ) {

            }

            Button(
                onClick = { viewModel.onClickNext() },
                modifier = Modifier
                    .size(50.dp)
                    .offset(80.dp, 40.dp),
                shape = directionalButtonsShape,
                border = BorderStroke(3.dp, BorderBlack),
                colors = ButtonDefaults.buttonColors(MainBlack)
            ) {

            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(50.dp)
                    .offset(40.dp, 80.dp),
                shape = directionalButtonsShape,
                border = BorderStroke(3.dp, BorderBlack),
                colors = ButtonDefaults.buttonColors(MainBlack)
            ) {

            }

            Surface(
                modifier = Modifier
                    .size(50.dp)
                    .offset(40.dp, 40.dp),
                shape = directionalButtonsShape,
                color = MainBlack
            ) {

            }

            Surface(
                modifier = Modifier
                    .size(34.dp)
                    .offset(48.dp, 48.dp),
                shape = CircleShape,
                color = MainGray
            ) {

            }
        }


    }
}

@Composable
fun PokemonBaseList(pokemons: List<Pokemon>, onClick: (Pokemon) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(90.dp),
        modifier = Modifier
            .padding(5.dp, 5.dp)
    ) {
        items(pokemons) { pokemon ->
            PokemonBaseCardList(pokemon, onClick)
        }
    }
}

@Composable
fun PokemonBaseCardList(pokemon: Pokemon, onClick: (Pokemon) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 5.dp)
            .wrapContentHeight()
            .wrapContentWidth()
            .border(4.dp, Black, cardShape)
            .clickable {
                onClick(pokemon)
            },
        colors = CardDefaults.cardColors(containerColor = MainBlue),
        shape = cardShape
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = pokemon.id.toString(),
                    modifier = Modifier
                        .background(Black, idShape)
                        .padding(5.dp, 2.dp)
                        .wrapContentWidth(),
                    color = White,
                    fontSize = 8.sp
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 2.dp),
                    text = pokemon.name.firstLetterUppercase(),
                    style = typography.titleSmall,
                    color = Black,
                    textAlign = TextAlign.Center,
                    fontSize = 7.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            AsyncImage(
                model = pokemon.gif,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(8.dp)
                    .size(60.dp),
                imageLoader = getImageLoader()
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp, 2.dp),
                text = pokemon.typesToString(),
                style = typography.bodyMedium,
                color = LightGray,
                fontSize = 8.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Composable
fun PokemonBaseCard(pokemon: Pokemon) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .fillMaxSize()
            .border(4.dp, Black, cardShape),
        colors = CardDefaults.cardColors(containerColor = MainBlue),
        shape = cardShape
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Text(
                    text = pokemon.id.toString(),
                    modifier = Modifier
                        .background(Black, idShape)
                        .padding(5.dp, 2.dp)
                        .wrapContentWidth(),
                    color = White,
                    fontSize = 8.sp
                )

                AsyncImage(
                    model = pokemon.gif,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(15.dp, 6.dp, 15.dp, 12.dp)
                        .fillMaxHeight()
                        .widthIn(0.dp, 120.dp),
                    imageLoader = getImageLoader()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 7.dp, 15.dp, 0.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp),
                    text = pokemon.name.firstLetterUppercase(),
                    style = typography.titleSmall,
                    color = Black,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = pokemon.typesToString(),
                    style = typography.bodyMedium,
                    color = LightGray,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

/*
@Preview
@Composable
fun PokemonCardPreview() {
    val types = listOf(PokemonType("fire"), PokemonType("flying"))
    val pokemon = PokemonBaseInfo(6, "charizard", types,
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png",
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/6.gif")

    PokemonBaseCard(pokemon = pokemon)
}
*/


