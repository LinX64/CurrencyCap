package com.client.currencycap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.chrisbanes.haze.HazeState
import ui.screens.main.exchange.ExchangeUiState
import ui.screens.main.exchange.components.ResultCard

@Composable
@Preview(showBackground = true)
private fun LineChartPreview() {
    val hazeState = remember { HazeState() }
    KoinPreview {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ResultCard(
                uiState = ExchangeUiState(),
                amount = "111.0",
            )
        }
    }
}
