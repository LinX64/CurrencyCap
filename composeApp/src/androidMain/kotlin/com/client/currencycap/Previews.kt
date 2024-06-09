package com.client.currencycap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import domain.model.DataDao
import domain.model.RateDao
import ui.screens.profile.ProfileCard

@Preview(showBackground = true, device = "id:pixel_3a")
@Composable
private fun SearchScreenPreview() {
    KoinPreview {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProfileCard()
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
