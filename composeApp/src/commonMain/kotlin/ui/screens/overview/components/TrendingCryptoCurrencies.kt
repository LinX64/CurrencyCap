package ui.screens.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.model.RateDto
import ui.screens.overview.OverviewState
import ui.theme.colors.CurrencyColors
import util.getIconBy

@Composable
internal fun TrendingCryptoCurrencies(rates: OverviewState) {
    Column {
        Text(
            modifier = if (rates is OverviewState.Loading) getPlaceHolder(
                Modifier.padding(
                    vertical = 16.dp,
                    horizontal = 8.dp
                ),
            ) else Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
            text = "Trending rates",
            style = MaterialTheme.typography.titleMedium,
            color = CurrencyColors.White,
            fontWeight = FontWeight.Bold
        )

        LazyHorizontalGrid(
            modifier = Modifier.fillMaxWidth()
                .heightIn(max = 300.dp)
                .widthIn(max = 370.dp, min = 350.dp),
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (rates is OverviewState.Success) {
                items(rates.cryptoRates.size) { index ->
                    val symbol = rates.cryptoRates[index].symbol

                    RateHorizontalItem(
                        icon = getIconBy(symbol),
                        rate = rates.cryptoRates[index],
                        isLoading = false
                    )
                }
            }

            if (rates is OverviewState.Loading) {
                items(5) {
                    RateHorizontalItem(
                        icon = getIconBy(""),
                        rate = RateDto(
                            currencySymbol = "",
                            id = "",
                            symbol = "",
                            rateUsd = "",
                            type = ""
                        ),
                        isLoading = true
                    )
                }
            }
        }
    }
}
