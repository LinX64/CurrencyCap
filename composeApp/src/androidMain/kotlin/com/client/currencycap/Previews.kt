package com.client.currencycap

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import dev.chrisbanes.haze.HazeState
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.components.tabs.MarketContent
import util.getDummyBonbastRates
import util.getDummyCryptoItems
import util.getDummyMarketRates
import util.getDummyNewsItem
import util.getDummyRatesItem

@Composable
@Preview(showBackground = true)
private fun ExchangePreview() {
    val hazeState = remember { HazeState() }

    KoinPreview {
        Column {
            MarketContent(
                onNewsItemClick = { /*TODO*/ },
                state = OverviewState.Success(
                    bonbastRates = getDummyBonbastRates(),
                    cryptoRates = getDummyCryptoItems(),
                    markets = getDummyMarketRates(),
                    fiatRates = getDummyRatesItem(),
                    news = listOf(getDummyNewsItem()),
                    topMovers = getDummyCryptoItems()
                )
            )
        }
    }
}


