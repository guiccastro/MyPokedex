package com.project.mypokedex.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.project.mypokedex.ui.theme.HomeScreenBackground

@Composable
fun Background() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = HomeScreenBackground
    ) {
    }
}