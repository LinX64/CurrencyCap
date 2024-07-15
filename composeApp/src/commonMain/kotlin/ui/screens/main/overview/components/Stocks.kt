package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import domain.model.main.Rate
import ui.screens.main.overview.OverviewState
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.AppDimensions.SPACER_PADDING_8
import ui.theme.colors.CurrencyColors
import util.getIconBy

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun Stocks(overviewState: OverviewState) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = CARD_CORNER_RADIUS)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(CARD_CORNER_RADIUS),
                text = "Stocks",
                style = MaterialTheme.typography.titleLarge,
                color = CurrencyColors.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(CARD_CORNER_RADIUS),
                text = "See all",
                style = MaterialTheme.typography.titleMedium,
                color = CurrencyColors.White,
                fontWeight = FontWeight.Bold
            )
        }

        FlowColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(CARD_CORNER_RADIUS),
            verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_8)
        ) {
            if (overviewState is OverviewState.Success) {
                overviewState.fiatRates.take(10).forEach { rate ->
                    StocksHorizontalItem(
                        icon = getIconBy(rate.symbol),
                        rate = rate,
                        isLoading = false
                    )
                }
            }

            if (overviewState is OverviewState.Loading) {
                repeat(10) {
                    StocksHorizontalItem(
                        icon = "",
                        isLoading = true,
                        rate = Rate(
                            currencySymbol = "",
                            id = "",
                            symbol = "",
                            rateUsd = "",
                            type = ""
                        )
                    )
                }
            }
        }
    }
}
