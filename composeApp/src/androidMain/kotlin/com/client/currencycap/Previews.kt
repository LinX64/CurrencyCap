package com.client.currencycap

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import dev.chrisbanes.haze.HazeState
import ui.components.CenteredColumn
import ui.screens.initial.landing.LandingScreen

@Composable
@Preview(showBackground = true)
private fun ExchangePreview() {
    val hazeState = remember { HazeState() }

    KoinPreview {
        CenteredColumn {
            LandingScreen(
                onLoginClick = {},
                onSignUpClick = {},
                onPrivacyPolicyClick = {})
        }
    }
}

