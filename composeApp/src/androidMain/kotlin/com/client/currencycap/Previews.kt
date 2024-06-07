package com.client.currencycap

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import domain.model.DataDao
import domain.model.RateDao
import ui.App
import ui.components.BlurBackground
import ui.screens.main.CryptoState
import ui.screens.main.HomeScreen
import ui.screens.main.MainState
import ui.theme.AppM3Theme

@Composable
@Preview(showBackground = true)
private fun AppPreview() {
    AppM3Theme(dark = true) {
        BlurBackground {
            HomeScreen(
                rates = MainState.Success(
                    listOf(
                        RateDao(
                            code = "USD",
                            sell = 44422,
                            buy = 44422,
                        )
                    )
                ),
                cryptoRates = CryptoState.Success(
                    listOf(
                        DataDao(
                            currencySymbol = "USD",
                            symbol = "USD",
                            id = "USD",
                            type = "USD",
                            rateUsd = "44422",
                        )
                    )
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun AppPreviewDark() {
    AppM3Theme(dark = true) {
        App()
    }
}


