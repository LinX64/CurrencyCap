package com.client.currencycap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import domain.model.DataDao
import domain.model.RateDao
import ui.components.BlurBackground
import ui.screens.exchange.ExchangeScreen
import ui.screens.main.components.PerformanceChart
import ui.theme.AppM3Theme

@Composable
@Preview(showBackground = true)
private fun AppChartPreview() {
    AppM3Theme(dark = true) {
        BlurBackground {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    PerformanceChart(
                        Modifier,
                        listOf(
                            113.518f,
                            113.799f,
                            113.333f,
                            113.235f,
                            114.099f,
                            113.506f,
                            113.985f,
                            114.212f,
                            114.125f
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ExchangePreview() {
    AppM3Theme(dark = true) {
        BlurBackground {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ExchangeScreen(padding = PaddingValues(16.dp))
            }
        }
    }
}

private val iranianDummyRate = listOf(
    RateDao(
        code = "USD",
        sell = 113,
        buy = 113
    ),
    RateDao(
        code = "EUR",
        sell = 113,
        buy = 113
    ),
)

private val cryptoDummyRate = listOf(
    DataDao(
        currencySymbol = "USD",
        id = "USD",
        rateUsd = "113",
        symbol = "USD",
        type = "USD"
    ),
    DataDao(
        currencySymbol = "EUR",
        id = "EUR",
        rateUsd = "113",
        symbol = "EUR",
        type = "EUR"
    ),
)
