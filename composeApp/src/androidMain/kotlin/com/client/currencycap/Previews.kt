package com.client.currencycap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import ui.components.base.button.PrimaryButton
import ui.components.base.button.PrimarySmallIconButton
import ui.components.base.button.SecondaryButton
import ui.components.base.button.SecondarySmallIconButton
import ui.screens.main.news.news_detail.components.NewsDetailContent
import util.getDummyNewsItem

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
private fun ButtonsPreview() {
    KoinPreview {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PrimaryButton(
                text = "Add to Watchlist",
                onButtonClick = {},
            )

            Spacer(modifier = Modifier.height(16.dp))

            PrimarySmallIconButton(
                text = "Add to Watchlist",
                onButtonClick = {},
            )

            Spacer(modifier = Modifier.height(16.dp))

            SecondaryButton(
                text = "Add to Watchlist",
                onButtonClick = {},
            )

            Spacer(modifier = Modifier.height(16.dp))

            SecondarySmallIconButton(
                text = "Add to Watchlist",
                onButtonClick = {},
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ExpandablePreview() {
    val hazeState = remember { HazeState() }
    KoinPreview {
        NewsDetailContent(
            article = getDummyNewsItem(),
            isLoading = false,
            imageUrl = null,
            onReadMoreClick = {},
        )
    }
}


