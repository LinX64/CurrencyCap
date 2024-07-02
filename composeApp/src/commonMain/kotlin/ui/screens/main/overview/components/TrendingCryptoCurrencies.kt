package ui.screens.main.overview.components

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
import ui.screens.main.overview.OverviewState
import ui.theme.colors.CurrencyColors
import util.getDummyCryptoItems

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun TrendingCryptoCurrencies(rates: OverviewState) {
    Column {
        Text(
            modifier = if (rates is OverviewState.Loading) getPlaceHolder(
                Modifier.padding(start = 8.dp),
            ) else Modifier.padding(start = 8.dp),
            text = "Trending rates",
            style = MaterialTheme.typography.titleMedium,
            color = CurrencyColors.White,
            fontWeight = FontWeight.Bold
        )

        FlowColumn(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (rates is OverviewState.Success) {
                rates.cryptoRates.take(50).forEachIndexed { index, rate -> // Takes only 50 items TODO: Add pagination
                    RateHorizontalItem(
                        crypto = rates.cryptoRates[index],
                        isLoading = false
                    )
                }
            }

            if (rates is OverviewState.Loading) {
                repeat(5) {
                    RateHorizontalItem(
                        crypto = getDummyCryptoItems()[it],
                        isLoading = true
                    )
                }
            }
        }
    }
}
