package com.project.mypokedex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.extensions.bottomBorder
import com.project.mypokedex.extensions.topBorder
import com.project.mypokedex.model.SettingsOptionItem
import com.project.mypokedex.navigation.MainNavComponent
import com.project.mypokedex.repository.LanguageRepository
import com.project.mypokedex.ui.scaffold.MainScaffold
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainRed
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB

@Composable
fun SettingsUIScreen() {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SettingsOptionItem.values().forEach { option ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .topBorder(1.dp, MainBlack)
                    .bottomBorder(1.dp, MainBlack)
                    .shadow(4.dp)
                    .clickable {
                        MainNavComponent.navController.apply {
                            option.screen.apply {
                                navigateToItself(
                                    navOptions = MainNavComponent.getSingleTopWithPopUpTo(
                                        getRoute()
                                    )
                                )
                            }
                        }
                    }
                    .background(MainRed)
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                verticalAlignment = CenterVertically
            ) {
                Text(
                    text = stringResource(id = option.title),
                    style = PokemonGB,
                    color = BlackTextColor,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .weight(1F)
                )

                if (option == SettingsOptionItem.LanguageSettingItem) {
                    Text(
                        text = LanguageRepository.getCurrentLanguage().toLanguageTag().uppercase(),
                        style = PokemonGB,
                        color = BlackTextColor.copy(alpha = 0.5F),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    MyPokedexTheme {
        MainScaffold {
            Surface {
                SettingsUIScreen()
            }
        }
    }
}