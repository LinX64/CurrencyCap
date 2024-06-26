package com.client.currencycap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.chrisbanes.haze.HazeState
import ui.components.main.BottomNavigationBar
import ui.theme.AppM3Theme

@Composable
@Preview(showBackground = true, device = "id:pixel_3a")
private fun CardPreview() {
    val hazeState = remember { HazeState() }

    AppM3Theme(dark = true) {
        DarkBackground {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Bottom
            ) {
                BottomNavigationBar(hazeState = hazeState) {}
            }
        }
    }
}

@Composable
private fun DarkBackground(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        content()
    }
}
