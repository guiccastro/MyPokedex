package com.project.mypokedex.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainRed
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB

@Composable
fun AboutUIScreen() {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text(
                text = stringResource(id = R.string.about_screen_about_app),
                style = PokemonGB,
                color = BlackTextColor,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                letterSpacing = 2.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            )

            Text(
                text = stringResource(id = R.string.about_screen_about_app_extra),
                style = PokemonGB,
                color = BlackTextColor,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                letterSpacing = 2.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            )
        }

        item {
            TitleSection(
                title = stringResource(id = R.string.about_screen_contact_title)
            )

            Text(
                text = stringResource(id = R.string.about_screen_contact),
                style = PokemonGB,
                color = BlackTextColor,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp,
                letterSpacing = 2.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            )

            val uriHandler = LocalUriHandler.current
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo_linkedin),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MainBlack),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp)
                        .clickable {
                            uriHandler.openUri("https://www.linkedin.com/in/guilhermescastro/")
                        }
                )
            }
        }

        item {
            TitleSection(
                title = stringResource(id = R.string.about_screen_disclaimer_title)
            )

            Text(
                text = stringResource(id = R.string.about_screen_disclaimer),
                style = PokemonGB,
                color = BlackTextColor,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                letterSpacing = 2.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            )

            Text(
                text = stringResource(id = R.string.about_screen_copyright),
                style = PokemonGB,
                color = BlackTextColor,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                letterSpacing = 2.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
            )
        }
    }
}

@Composable
fun TitleSection(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Divider(
            thickness = 1.dp,
            color = MainBlack,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "  " + title.uppercase() + "  ",
                style = PokemonGB,
                color = BlackTextColor,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(MainRed)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AboutUiScreenPreview() {
    MyPokedexTheme {
        Surface {
            AboutUIScreen()
        }
    }
}