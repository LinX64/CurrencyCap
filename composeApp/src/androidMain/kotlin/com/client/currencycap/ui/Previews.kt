package com.client.currencycap.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import domain.model.DataDao
import domain.model.RateDao
import ui.App
import ui.components.BlurDarkBackground
import ui.components.BlurLightBackground
import ui.screens.main.CryptoState
import ui.screens.main.HomeScreen
import ui.screens.main.MainState
import ui.theme.AppM3Theme

@Preview
@Composable
fun MainScreenPreviewLight() {
    AppM3Theme(dark = true) {
        BlurDarkBackground {
            HomeScreen(
                rates = MainState.Success(
                    listOf(
                        RateDao("USD", buy = 58000, sell = 58000),
                        RateDao("EUR", buy = 68000, sell = 68000),
                        RateDao("JPY", buy = 48000, sell = 48000)
                    )
                ),
                cryptoRates = CryptoState.Success(
                    listOf(
                        DataDao(
                            currencySymbol = "BTC",
                            symbol = "BTC",
                            rateUsd = "5800",
                            type = "crypto",
                            id = "1",
                        )
                    )
                )
            )
        }
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

// preview for app
@Preview
@Composable
fun AppPreview() {
    AppM3Theme(dark = false) {
        BlurLightBackground {
            App()
        }
    }
}


