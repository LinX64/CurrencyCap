package com.client.currencycap

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import domain.model.DataDao
import domain.model.RateDao
import ui.App
import ui.screens.components.BlurBackground
import ui.screens.main.HomeScreen
import ui.screens.main.MainState
import ui.theme.AppM3Theme

@Composable
@Preview(showBackground = true)
private fun AppPreview() {
    AppM3Theme(dark = true) {
        BlurBackground {
            HomeScreen(
                state = MainState.Success(
                    listOf(
                        RateDao(
                            code = "USD",
                            sell = 44422,
                            buy = 44422,
                        )
                    ),
                    listOf(
                        DataDao(
                            currencySymbol = "BTC",
                            id = "1",
                            rateUsd = "44422.0",
                            symbol = "BTC",
                            type = "crypto"
                        )
                    )
                ),
                padding = PaddingValues(0.dp, 0.dp, 0.dp, 0.dp)
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


