package ui.screens.home.components

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
import androidx.compose.ui.unit.dp
import ui.screens.home.MainState
import ui.theme.colors.CurrencyColors
import util.getIconBy

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun Stocks(mainState: MainState) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Stocks",
                style = MaterialTheme.typography.titleLarge,
                color = CurrencyColors.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(16.dp),
                text = "See all",
                style = MaterialTheme.typography.titleMedium,
                color = CurrencyColors.White,
                fontWeight = FontWeight.Bold
            )
        }

        FlowColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (mainState is MainState.Success) {
                mainState.cryptoRates.take(10).forEach { rate ->
                    StocksHorizontalItem(
                        icon = getIconBy(rate.symbol),
                        rate = rate
                    )
                }
            }
        }
    }
}
