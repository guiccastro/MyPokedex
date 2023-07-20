package com.project.mypokedex.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.project.mypokedex.model.PokemonType

@Composable
fun PokemonTypeToUI(
    pokemonType: PokemonType,
    size: Dp = 18.dp
) {
    val padding = (size.value * 0.2).dp

    Image(
        painter = painterResource(id = pokemonType.getIcon()),
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .background(pokemonType.getColor(), CircleShape)
            .padding(padding)
    )
}

@Preview
@Composable
fun TypePreview() {
    PokemonTypeToUI(PokemonType.Fire)
}