package com.client.currencycap

import domain.model.DataDao
import ui.screens.main.CryptoState

val rates = CryptoState.Success(
    listOf(
        DataDao(
            currencySymbol = "USD",
            symbol = "USD",
            id = "USD",
            type = "USD",
            rateUsd = "44422",
        )
    )
)

//@Composable
//@Preview(showBackground = true)
//private fun AppPreview() {
//    AppM3Theme(dark = true) {
//        BlurBackground {
//            HomeScreen(
//                rates = MainState.Success(
//                    listOf(
//                        RateDao(
//                            code = "USD",
//                            sell = 44422,
//                            buy = 44422,
//                        )
//                    )
//                ),
//                cryptoRates = CryptoState.Success(
//                    listOf(
//                        DataDao(
//                            currencySymbol = "USD",
//                            symbol = "USD",
//                            id = "USD",
//                            type = "USD",
//                            rateUsd = "44422",
//                        )
//                    )
//                )
//            )
//        }
//    }
//}
