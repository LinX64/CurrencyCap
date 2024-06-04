package com.client.currencycap.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import domain.model.RateDao
import ui.components.BulbBackground
import ui.screens.HomeScreen
import ui.screens.MainState
import ui.screens.SearchUiState
import ui.theme.AppM3Theme

@Preview
@Composable
fun MainScreenPreviewLight() {
    AppM3Theme(dark = false) {
        BulbBackground {
            HomeScreen(
                rates = MainState.Success(
                    listOf(
                        RateDao("USD", buy = 58000, sell = 58000),
                        RateDao("EUR", buy = 68000, sell = 68000),
                        RateDao("JPY", buy = 48000, sell = 48000),
                        RateDao("GBP", buy = 78000, sell = 78000),
                        RateDao("CNY", buy = 38000, sell = 38000),
                        RateDao("KRW", buy = 28000, sell = 28000),
                    )
                ),
                searchResultState = SearchUiState.SearchNotReady,
                onSearchClick = {},
                onClear = {},
                onItemClick = {}
            )
        }
    }
}
