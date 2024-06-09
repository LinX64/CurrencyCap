package com.client.currencycap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import domain.model.DataDao
import domain.model.RateDao
import ui.screens.home.components.RateHorizontalItem

//@OptIn(ExperimentalLayoutApi::class)
//@Preview(showBackground = true, device = "id:pixel_3a")
//@Composable
//private fun SearchScreenPreview() {
//    KoinPreview {
//        Column {
//            FlowColumn(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(16.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                cryptoDummyRate.forEach { rate ->
//                    StocksHorizontalItem(
//                        icon = rate.symbol,
//                        rate = rate
//                    )
//                }
//            }
//        }
//    }
//}

@Preview(showBackground = true, device = "id:pixel_3a")
@Composable
private fun HorizontalRateItemPreview() {
    KoinPreview {
        Column {
            LazyHorizontalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp),
                rows = GridCells.Fixed(3),
                contentPadding = PaddingValues(10.dp),
            ) {
                items(iranianDummyRate) { rate ->
                    RateHorizontalItem(
                        icon = rate.code,
                        rate = DataDao(
                            currencySymbol = rate.code,
                            id = rate.code,
                            rateUsd = rate.sell.toString(),
                            symbol = rate.code,
                            type = rate.code
                        )
                    )
                }
            }
        }
    }
}

private val iranianDummyRate = listOf(
    RateDao(
        code = "USD",
        sell = 113,
        buy = 113
    ),
    RateDao(
        code = "EUR",
        sell = 113,
        buy = 113
    ),
    RateDao(
        code = "EUR",
        sell = 113,
        buy = 113
    ),
    RateDao(
        code = "EUR",
        sell = 113,
        buy = 113
    ),
    RateDao(
        code = "EUR",
        sell = 113,
        buy = 113
    ),
    RateDao(
        code = "EUR",
        sell = 113,
        buy = 113
    ),
)
private val cryptoDummyRate = listOf(
    DataDao(
        currencySymbol = "USD",
        id = "USD",
        rateUsd = "113",
        symbol = "USD",
        type = "USD"
    ),
    DataDao(
        currencySymbol = "EUR",
        id = "EUR",
        rateUsd = "113",
        symbol = "EUR",
        type = "EUR"
    ),
)
