package com.project.mypokedex.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.extensions.bottomBorder
import com.project.mypokedex.extensions.topBorder
import com.project.mypokedex.model.LanguageUIOption
import com.project.mypokedex.repository.LanguageRepository
import com.project.mypokedex.ui.scaffold.MainScaffold
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainRed
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB

@Composable
fun LanguageUIScreen() {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        LanguageUIOption.values().forEach { option ->
            val context = LocalContext.current

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .topBorder(1.dp, MainBlack)
                    .bottomBorder(1.dp, MainBlack)
                    .shadow(4.dp)
                    .clickable {
                        LanguageRepository.changeLanguage(context, option.languageOption.locale)
                    }
                    .background(MainRed.copy(alpha = 1F))
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            ) {
                Text(
                    text = stringResource(id = option.title),
                    style = PokemonGB,
                    color = BlackTextColor,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .weight(1F)
                )

                if (option.languageOption.locale == LanguageRepository.getCurrentLanguage()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MainBlack)
                    )
                }
            }


        }
    }
}

@Preview
@Composable
fun LanguageUIScreenPreview() {
    MyPokedexTheme {
        MainScaffold {
            LanguageUIScreen()
        }
    }
}