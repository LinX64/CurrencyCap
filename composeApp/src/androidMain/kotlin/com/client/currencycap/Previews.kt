package com.client.currencycap

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ui.screens.auth.login.LoginScreen

@Preview(showBackground = true, device = "id:pixel_3a")
@Composable
private fun LoginScreenPreview() {
    KoinPreview {
        LoginScreen(onNavigateToMarketOverview = {}, onError = {})
    }
}

