package com.project.mypokedex.ui.components.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mypokedex.sampledata.bottomAppItemsSample
import com.project.mypokedex.ui.components.customShadow
import com.project.mypokedex.ui.components.topBorder
import com.project.mypokedex.ui.theme.BorderBlack
import com.project.mypokedex.ui.theme.MyPokedexTheme

enum class BottomAppBarItem(
    val label: String,
    val icon: ImageVector
) {
    GridScreen(label = "Grid", icon = Icons.Default.List),
    SimpleScreen(label = "Simple", icon = Icons.Default.AccountBox);
}

@Composable
fun BottomBar(bottomList: List<BottomAppBarItem> = emptyList()) {
    BottomAppBar(
        modifier = Modifier
            .topBorder(1.dp, BorderBlack)
            .customShadow(
                color = Color.Black.copy(alpha = 0.7f),
                blurRadius = 5.dp,
                widthOffset = 10.dp
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            bottomList.forEach {
                BottomAppBarItem(it)
            }
        }
    }
}

@Composable
fun BottomAppBarItem(item: BottomAppBarItem) {
    Column(
        modifier = Modifier
            .widthIn(min = 20.dp, max = 120.dp)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = item.icon,
                contentDescription = item.name,
                modifier = Modifier
                    .size(40.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.label,
                fontWeight = FontWeight(500),
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
                    BottomBar(bottomAppItemsSample)
                }
            ) {
                Surface(modifier = Modifier.padding(it)) {

                }
            }
        }
    }
}

@Preview
@Composable
fun BottomAppBarItemPreview() {
    MyPokedexTheme {
        Surface {
            BottomAppBarItem(BottomAppBarItem.GridScreen)
        }
    }
}