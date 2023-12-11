package com.project.mypokedex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.R
import com.project.mypokedex.model.SettingsOptionItem
import com.project.mypokedex.navigation.screens.AboutScreen
import com.project.mypokedex.ui.scaffold.MainScaffold
import com.project.mypokedex.ui.stateholders.SettingsScreenUIState
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MainRed
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB

@Composable
fun SettingsUIScreen(state: SettingsScreenUIState) {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        state.options.forEach { option ->
            Text(
                text = stringResource(id = option.title),
                style = PokemonGB,
                color = BlackTextColor,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MainBlack)
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .shadow(4.dp)
                    .background(MainRed.copy(alpha = 1F))
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    MyPokedexTheme {
        MainScaffold {
            Surface {
                SettingsUIScreen(
                    SettingsScreenUIState(
                        options = listOf(
                            SettingsOptionItem(
                                title = R.string.settings_language_title,
                                screen = AboutScreen
                            ),
                            SettingsOptionItem(
                                title = R.string.drawer_item_settings_title,
                                screen = AboutScreen
                            )
                        )
                    )
                )
            }
        }
    }
}