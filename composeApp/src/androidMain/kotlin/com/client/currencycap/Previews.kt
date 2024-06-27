package com.client.currencycap

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import data.model.exchange.CurrencyType
import dev.chrisbanes.haze.HazeState
import ui.screens.settings.SettingsScreen

@Composable
@Preview(showBackground = true)
private fun ExchangePreview() {
    val hazeState = remember { HazeState() }
    var selectedCurrencyType: CurrencyType by remember { mutableStateOf(CurrencyType.None) }

    KoinPreview {
        SettingsScreen(
            padding = PaddingValues(16.dp),
            onNavigateToLanding = {},
            onError = {},
            hazeState = hazeState,
        )
    }
}

