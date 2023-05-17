package com.project.mypokedex.data

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.project.mypokedex.R
import com.project.mypokedex.ui.theme.PokemonGB
import com.project.mypokedex.ui.theme.getImageLoader

class Pokemon(
    val id: Int,
    val name: String,
    val types: List<PokemonType>,
    val image: String,
    val gif: String
) {
    override fun toString(): String {
        return "$id|$name|$types|$image"
    }

    fun typesToString(): String {
        var string = ""
        types.forEachIndexed { index, pokemonType ->
            string += pokemonType.toString()
            if (index != types.size - 1) {
                string += " "
            }
        }
        return string
    }

    private fun formattedID(): String {
        return "# ${id.toString().padStart(3, '0')}"
    }

    private fun formattedName(): String {
        return name.uppercase()
    }

    @Composable
    fun ToCard() {
        Surface(
            modifier = Modifier
                .height(150.dp)
                .width(340.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_screen_background),
                contentDescription = "Screen Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )

            Row(modifier = Modifier.padding(horizontal = 6.dp)) {
                Box(modifier = Modifier.size(150.dp)) {
                    /*
                    Image(
                        painter = painterResource(id = R.drawable.ic_charizard),
                        contentDescription = "Pokemon Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(4.dp)
                            .border(1.dp, Color.Black)
                    )
                     */

                    AsyncImage(
                        model = gif,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(4.dp),
                        imageLoader = getImageLoader()
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
                            text = formattedID(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                            color = Color.DarkGray,
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = PokemonGB
                        )
                        Text(
                            text = formattedName(),
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
                        items(types) {
                            it.ToUI(fontSize = 12.sp)
                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun PokemonCardPreview() {
    Pokemon(
        6,
        "charizard",
        listOf(PokemonType.Fire, PokemonType.Flying, PokemonType.Grass),
        "",
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/6.gif"
    ).ToCard()
}