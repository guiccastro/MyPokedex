package com.project.mypokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.model.PokemonGeneration
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainWhite
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB

@Composable
fun PokemonGenerationToUI(
    pokemonGeneration: PokemonGeneration,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 24.sp,
    borderPadding: PaddingValues = PaddingValues(4.dp)
) {
    val shape = RoundedCornerShape(4.dp)
    Text(
        text = stringResource(id = R.string.generation, pokemonGeneration.id),
        style = PokemonGB,
        color = BlackTextColor,
        fontWeight = FontWeight.Bold,
        fontSize = fontSize,
        modifier = modifier
            .background(MainWhite, shape)
            .border(1.dp, MainBlack, shape)
            .padding(borderPadding)
    )
}

@Preview
@Composable
fun PokemonGenerationToUIPreview() {
    MyPokedexTheme {
        Surface {
            PokemonGenerationToUI(PokemonGeneration.Gen1)
        }
    }
}