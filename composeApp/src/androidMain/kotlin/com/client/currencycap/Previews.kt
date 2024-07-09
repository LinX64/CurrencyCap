package com.client.currencycap

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import dev.chrisbanes.haze.HazeState

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

@Composable
@Preview(showBackground = true)
private fun DetailPreview() {
    val hazeState = remember { HazeState() }

    KoinPreview {

    }
}


