package com.client.currencycap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.chrisbanes.haze.HazeState
import kotlinx.collections.immutable.persistentListOf
import ui.screens.main.overview.OverviewScreen
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.components.CryptoHorizontalItem
import util.getDummyBonbastRates
import util.getDummyCryptoItem
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
                state = OverviewState.Success(
                    bonbastRates = getDummyBonbastRates(),
                    cryptoRates = getDummyCryptoItems(),
                    markets = getDummyMarketRates(),
                    fiatRates = getDummyRatesItem(),
                    news = persistentListOf(getDummyNewsItem()),
                    topMovers = getDummyCryptoItems()
                ),
                hazeState = hazeState,
                onNewsItemClick = { /*TODO*/ },
                onSearchCardClicked = {},
                onCircleButtonClicked = {},
                onCryptoItemClick = { _, _ -> },
            )
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//private fun ButtonsPreview() {
//    KoinPreview {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            PrimaryButton(
//                text = "Add to Watchlist",
//                onButtonClick = {},
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            PrimarySmallIconButton(
//                text = "Add to Watchlist",
//                onButtonClick = {},
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            SecondaryButton(
//                text = "Add to Watchlist",
//                onButtonClick = {},
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            SecondarySmallIconButton(
//                text = "Add to Watchlist",
//                onButtonClick = {},
//            )
//        }
//    }
//}

@Composable
@Preview(showBackground = true)
private fun LineChartPreview() {
    KoinPreview {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),

                ) {
                items(5) {
                    CryptoHorizontalItem(
                        crypto = getDummyCryptoItem(),
                        onClick = { _, _ -> },
                    )
                }
            }
        }
    }
}
