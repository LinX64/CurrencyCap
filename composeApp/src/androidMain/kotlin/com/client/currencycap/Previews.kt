package com.client.currencycap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        NewsFilterSection()
    }
}

@Composable
private fun NewsFilterSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Filter by",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )


    }
}




