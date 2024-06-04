package ui.screens.main.components

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
import androidx.compose.ui.unit.dp
import ui.screens.main.CryptoState
import ui.theme.colors.CurrencyColors

@Composable
internal fun TrendingCryptoCurrencies(rates: CryptoState) {
    Column {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = "Trending rates",
            style = MaterialTheme.typography.titleLarge,
            color = CurrencyColors.White
        )

        LazyHorizontalGrid(
            modifier = Modifier.fillMaxWidth()
                .heightIn(max = 300.dp)
                .widthIn(max = 300.dp, min = 300.dp),
            rows = GridCells.Fixed(3),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (rates is CryptoState.Success) {
                items(rates.rates.size) { index ->
                    val symbol = rates.rates[index].symbol

                    RateHorizontalItem(
                        rate = rates.rates[index],
                        icon = getIconBy(symbol)
                    )
                }
            }
        }
    }
}
