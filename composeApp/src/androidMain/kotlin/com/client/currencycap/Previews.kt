package com.client.currencycap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ui.components.InteractiveCryptoChart

//@Composable
//@Preview(showBackground = true)
//private fun ExchangePreview() {
//    val hazeState = remember { HazeState() }
//
//    KoinPreview {
//        Column {
//            OverviewScreen(
//                state = OverviewState.Success(
//                    bonbastRates = getDummyBonbastRates(),
//                    cryptoRates = getDummyCryptoItems(),
//                    markets = getDummyMarketRates(),
//                    fiatRates = getDummyRatesItem(),
//                    news = persistentListOf(getDummyNewsItem()),
//                    topMovers = getDummyCryptoItems()
//                ),
//                hazeState = hazeState,
//                onNewsItemClick = { /*TODO*/ },
//                onSearchCardClicked = {},
//                onCircleButtonClicked = {},
//                onCryptoItemClick = { _, _ -> },
//            )
//        }
//    }
//}

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
            val isLoading = false
            InteractiveCryptoChart(
                chartData = setOf(),
                isLoading = isLoading,
            )
        }
    }
}
