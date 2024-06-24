package com.client.currencycap

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.chrisbanes.haze.HazeState
import ui.components.BaseCenterColumn
import ui.components.main.BottomNavigationBar

@Preview(showBackground = true, device = "id:pixel_3a")
@Composable
private fun LoginScreenPreview() {
    KoinPreview {
        BaseCenterColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            BottomNavigationBar(
                hazeState = HazeState(),
                onTabSelected = {}
            )
        }
    }
}

