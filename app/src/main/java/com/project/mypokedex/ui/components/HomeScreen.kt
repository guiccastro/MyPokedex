package com.project.mypokedex.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.mypokedex.PokedexViewModel
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.BorderBlackShadow
import com.project.mypokedex.ui.theme.Green
import com.project.mypokedex.ui.theme.HeavyRed
import com.project.mypokedex.ui.theme.LightGray
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainBlue
import com.project.mypokedex.ui.theme.MainGray
import com.project.mypokedex.ui.theme.MainRed
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.Transparent
import com.project.mypokedex.ui.theme.Yellow
import com.project.mypokedex.ui.theme.directionalButtonsShape
import com.project.mypokedex.ui.theme.pokedexScreenShape

@Composable
fun HomeScreen(viewModel: PokedexViewModel) {
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
                .padding(start = 15.dp, top = 30.dp, end = 15.dp, bottom = 80.dp),
            color = LightGray
        ) {
            viewModel.currentPokemonInfo?.ToCard()
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
            color = Color.White
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
                onClick = { viewModel.onClickUp() },
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
                onClick = { viewModel.onClickDown() },
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

@Preview
@Composable
fun HomeScreenPreview() {
    val viewModel = PokedexViewModel()
    MyPokedexTheme {
        Surface {
            HomeScreen(viewModel = viewModel)
        }
    }
}