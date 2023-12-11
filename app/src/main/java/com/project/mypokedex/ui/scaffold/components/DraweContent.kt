package com.project.mypokedex.ui.scaffold.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.model.DrawerMenuItem
import com.project.mypokedex.model.MainDrawerMenuComponent
import com.project.mypokedex.model.MainDrawerMenuComponent.onClickDrawerMenuItem
import com.project.mypokedex.model.MainTopAppBarComponent
import com.project.mypokedex.ui.components.AppNameCard
import com.project.mypokedex.ui.stateholders.DrawerMenuUIState
import com.project.mypokedex.ui.theme.BlackTextColor
import com.project.mypokedex.ui.theme.MainBlack
import com.project.mypokedex.ui.theme.MyPokedexTheme
import com.project.mypokedex.ui.theme.PokemonGB
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(state: DrawerMenuUIState) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
    ) {
        AppNameCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 60.dp)
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            thickness = 1.dp,
            color = MainBlack
        )

        MainDrawerMenuComponent.drawerItems.forEach { drawerItem ->

            NavigationDrawerItem(
                label = {
                    Text(
                        text = stringResource(id = drawerItem.title),
                        style = PokemonGB,
                        fontSize = 14.sp,
                        color = BlackTextColor,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                icon = {
                    Image(
                        painter = painterResource(id = drawerItem.icon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(vertical = 10.dp),
                        colorFilter = ColorFilter.tint(MainBlack)
                    )
                },
                modifier = Modifier
                    .height(50.dp),
                selected = drawerItem == state.itemSelected,
                onClick = {
                    onClickDrawerMenuItem(drawerItem)
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MainBlack.copy(alpha = 0.2F)
                )
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DrawerContentPreview() {
    MyPokedexTheme {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier
                        .width(300.dp)
                ) {
                    DrawerContent(
                        DrawerMenuUIState(
                            itemSelected = DrawerMenuItem.SettingsDrawerMenuItem
                        )
                    )
                }
            },
        ) {
            Scaffold(
                topBar = {
                    TopBar(
                        state = MainTopAppBarComponent.topAppBarState(),
                        onMenuClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Surface(
                    modifier = Modifier
                        .padding(paddingValues)
                ) {
                }
            }
        }
    }
}