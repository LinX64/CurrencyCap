package com.client.currencycap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import ui.screens.main.overview.OverviewScreen
import ui.screens.main.overview.OverviewState
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
            OverviewScreen(
                padding = PaddingValues(0.dp),
                hazeState = hazeState,
                onSearchCardClicked = { /*TODO*/ },
                onNewsItemClick = { /*TODO*/ },
                onCircleButtonClicked = { /*TODO*/ },
                onRetry = { /*TODO*/ },
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


