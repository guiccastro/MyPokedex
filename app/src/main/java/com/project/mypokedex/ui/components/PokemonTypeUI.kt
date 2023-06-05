package com.project.mypokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.model.PokemonType
import com.project.mypokedex.ui.theme.PokemonGB

@Composable
fun PokemonTypeToUI(
    pokemonType: PokemonType,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 6.sp
) {
    val cornerRoundWeight = 3 / 4
    val cornerShape = RoundedCornerShape((fontSize.value * cornerRoundWeight).dp)
    Text(
        modifier = modifier
            .border(width = (fontSize.value / 35).dp, Color.Black, cornerShape)
            .border(width = (fontSize.value / 7).dp, color = pokemonType.getColor(), cornerShape)
            .background(Color.Black.copy(alpha = 0.5f), cornerShape)
            .padding(
                horizontal = (fontSize.value * 2 / 3).dp,
                vertical = (fontSize.value / 2).dp
            ),
        text = pokemonType.toString().uppercase(),
        color = pokemonType.getColor(),
        fontSize = fontSize,
        fontWeight = FontWeight(1000),
        style = PokemonGB
    )
}

@Preview
@Composable
fun TypePreview() {
    PokemonTypeToUI(PokemonType.Fire)
}