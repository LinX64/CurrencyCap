package com.client.currencycap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import ui.screens.main.detail.ChartDataUiState
import ui.screens.main.detail.DetailScreen
import ui.screens.main.detail.DetailState
import util.getDummyChartData
import util.getDummyCryptoInfo
import util.getDummyCryptoItem

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
//                    news = listOf(getDummyNewsItem()),
//                    topMovers = getDummyCryptoItems()
//                ),
//                hazeState = hazeState,
//                onNewsItemClick = { /*TODO*/ },
//                padding = PaddingValues(0.dp),
//                onSearchCardClicked = {},
//                onCircleButtonClicked = {},
//                onRetry = {},
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
private fun ExpandablePreview() {
    val hazeState = remember { HazeState() }
    KoinPreview {
        Column {
            DetailScreen(
                state = DetailState.Success(
                    crypto = getDummyCryptoItem(),
                    chartData = ChartDataUiState(
                        data = getDummyChartData()
                    ),
                    cryptoInfo = getDummyCryptoInfo(),
                ),
                hazeState = hazeState,
                handleEvent = {},
                padding = PaddingValues(0.dp),
            )
        }
    }
}


