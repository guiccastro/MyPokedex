package com.project.mypokedex.ui.scaffold.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.mypokedex.model.BottomAppBarItem
import com.project.mypokedex.sampledata.bottomAppItemsSample
import com.project.mypokedex.ui.components.customShadow
import com.project.mypokedex.ui.components.topBorder
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.BottomAppBarSelectedItem
import com.project.mypokedex.ui.theme.MyPokedexTheme

@Composable
fun BottomBar(
    selectedItem: BottomAppBarItem? = null,
    items: List<BottomAppBarItem> = emptyList(),
    onClickItem: (BottomAppBarItem) -> Unit = {}
) {
    NavigationBar(
        modifier = Modifier
            .topBorder(1.dp, BorderBlack)
            .customShadow(
                color = Color.Black.copy(alpha = 0.7f),
                blurRadius = 5.dp,
                widthOffset = 10.dp
            )
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem == item,
                onClick = {
                    onClickItem(item)
                },
                icon = {
                    Image(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontWeight = FontWeight(500),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = BottomAppBarSelectedItem
                )
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun BottomAppBarPreview() {
    MyPokedexTheme {
        Surface {
            Scaffold(
                bottomBar = {
                    BottomBar(items = bottomAppItemsSample)
                }
            ) {
                Surface(modifier = Modifier.padding(it)) {

                }
            }
        }
    }
}