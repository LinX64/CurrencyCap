package com.client.currencycap

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ui.screens.landing.LandingScreen

@Composable
@Preview(showBackground = true, device = "id:pixel_3a")
private fun LandingScreenPreview() {
    KoinPreview {
        LandingScreen(
            onLoginClick = {},
            onSignUpClick = {}
        )
    }
}

