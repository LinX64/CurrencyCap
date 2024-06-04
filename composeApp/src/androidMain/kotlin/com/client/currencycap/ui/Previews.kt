package com.client.currencycap.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import domain.model.RateDao
import ui.screens.main.CryptoState
import ui.screens.main.HomeScreen
import ui.screens.main.MainState
import ui.screens.main.components.IranianRate
import ui.theme.AppM3Theme

@Preview
@Composable
fun MainScreenPreviewLight() {
    AppM3Theme(dark = true) {
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
            cryptoRates = CryptoState.Loading
        )
    }
}

//@Preview
//@Composable
//fun MainScreenPreviewDark() {
//    AppM3Theme(dark = true) {
//        BulbBackground {
//            Column {
//                Text(
//                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
//                    text = "Top rates",
//                    style = MaterialTheme.typography.titleLarge,
//                    color = CurrencyColors.White
//                )
//
//                LazyHorizontalGrid(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .heightIn(max = 250.dp),
//                    rows = GridCells.Fixed(1),
//                    contentPadding = PaddingValues(16.dp),
//                    horizontalArrangement = Arrangement.spacedBy(16.dp)
//                ) {
//                    items(6) { index ->
//                        RateItem(rate = RateDao("USD", buy = 58000, sell = 58000))
//                    }
//                }
//            }
//        }
//    }
//}

//@Preview
//@Composable
//fun MainScreenPreviewDarkError() {
//    AppM3Theme(dark = true) {
//        BulbBackground {
//            Column {
//                RateItem(rate = RateDao("USD", buy = 58000, sell = 58000))
//            }
//        }
//    }
//}

@Preview
@Composable
fun MainScreenPreviewDarkError() {
    AppM3Theme(dark = true) {
        Column {
            IranianRate(
                rates = MainState.Success(
                    listOf(
                        RateDao("USD", buy = 58000, sell = 58000),
                        RateDao("EUR", buy = 68000, sell = 68000),
                        RateDao("JPY", buy = 48000, sell = 48000),
                        RateDao("GBP", buy = 78000, sell = 78000),
                        RateDao("CNY", buy = 38000, sell = 38000),
                        RateDao("KRW", buy = 28000, sell = 28000),
                    )
                )
            )
        }
    }
}



