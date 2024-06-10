package com.client.currencycap

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ui.screens.landing.LandingScreen
import ui.screens.subscribers.SubscribersSection

@Preview(showBackground = true, device = "id:pixel_3a")
@Composable
private fun SubscribersScreenPreview() {
    KoinPreview {
        SubscribersSection()
    }
}

@Preview(showBackground = true, device = "id:pixel_3a")
@Composable
private fun LandingScreenPreview() {
    KoinPreview {
        LandingScreen(
            onLoginClick = {},
            onCreateAccountClick = {}
        )
    }
}
