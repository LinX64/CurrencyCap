package com.client.currencycap

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import domain.model.DataDao
import domain.model.RateDao
import ui.screens.auth.register.RegisterScreen

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
    RateDao(
        code = "EUR",
        sell = 113,
        buy = 113
    ),
    RateDao(
        code = "EUR",
        sell = 113,
        buy = 113
    ),
    RateDao(
        code = "EUR",
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

@Preview(showBackground = true, device = "id:pixel_3a")
@Composable
private fun RegisterScreenPreview() {
    KoinPreview {
        RegisterScreen()
    }
}

