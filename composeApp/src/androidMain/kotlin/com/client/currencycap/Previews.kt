package com.client.currencycap

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ui.screens.exchange.ExchangeScreen

@Preview(showBackground = true, device = "id:pixel_3a")
@Composable
private fun LoginScreenPreview() {
    KoinPreview {
        ExchangeScreen(padding = PaddingValues(16.dp))
    }
}

