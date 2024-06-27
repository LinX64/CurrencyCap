package ui.screens.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun TrendingCryptoCurrencies(rates: OverviewState) {
    Column {
        Text(
            modifier = if (rates is OverviewState.Loading) getPlaceHolder(
                Modifier.padding(top = 16.dp, start = 8.dp),
            ) else Modifier.padding(top = 16.dp, start = 8.dp),
            text = "Trending rates",
            style = MaterialTheme.typography.titleMedium,
            color = CurrencyColors.White,
            fontWeight = FontWeight.Bold
        )

        FlowColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (rates is OverviewState.Success) {
                rates.cryptoRates.forEachIndexed { index, rate ->
                    val symbol = rates.cryptoRates[index].symbol

                    RateHorizontalItem(
                        icon = getIconBy(symbol),
                        rate = rates.cryptoRates[index],
                        isLoading = false
                    )
                }
            }

            if (rates is OverviewState.Loading) {
                repeat(5) {
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
