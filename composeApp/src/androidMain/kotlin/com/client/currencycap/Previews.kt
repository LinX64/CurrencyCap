package com.client.currencycap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ui.components.base.button.PrimaryButton
import ui.components.base.button.PrimarySmallIconButton
import ui.components.base.button.SecondaryButton
import ui.components.base.button.SecondarySmallIconButton

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

@Composable
@Preview(showBackground = true)
private fun ButtonsPreview() {
    KoinPreview {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PrimaryButton(
                isLoading = true,
                text = "Add to Watchlist",
                onButtonClick = {},
            )

            PrimaryButton(
                text = "Add to Watchlist",
                onButtonClick = {},
            )

            PrimarySmallIconButton(
                text = "Add to Watchlist",
                onButtonClick = {},
            )

            SecondaryButton(
                isLoading = true,
                text = "Add to Watchlist",
                onButtonClick = {},
            )

            SecondaryButton(
                text = "Add to Watchlist",
                onButtonClick = {},
            )

            SecondarySmallIconButton(
                text = "Add to Watchlist",
                onButtonClick = {},
            )
        }
    }
}
