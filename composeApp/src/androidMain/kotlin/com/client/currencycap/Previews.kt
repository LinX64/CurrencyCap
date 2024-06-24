package com.client.currencycap

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ui.screens.landing.LandingScreen

@Preview(showBackground = true, device = "id:pixel_3a")
@Composable
private fun LoginScreenPreview() {
    KoinPreview {
        LandingScreen(
            onLoginClick = {},
            onCreateAccountClick = {}
        )
    }
}

