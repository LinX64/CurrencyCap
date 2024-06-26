package com.client.currencycap

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import ui.screens.profile.ProfileScreen

@Composable
@Preview(showBackground = true, device = "id:pixel_3a")
private fun ExchangePreview() {
    val hazeState = remember { HazeState() }
    KoinPreview {
        ProfileScreen(
            padding = PaddingValues(16.dp),
            hazeState = hazeState,
            onError = {},
            onNavigateToLanding = {}
        )
    }
}

