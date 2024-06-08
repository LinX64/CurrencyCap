package ui.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.screens.main.MainState

@Composable
fun IranianRate(rates: MainState) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Top rates",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Text(
                    text = "Iranian currency",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
            )
        }

        LazyHorizontalGrid(
            modifier = Modifier.fillMaxWidth()
                .heightIn(max = 180.dp),
            rows = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (rates is MainState.IranianRateSuccess) {
                items(rates.ratesList.size) { index ->
                    val code = rates.ratesList[index].code
                    RateItem(
                        rate = rates.ratesList[index],
                        icon = getIconBy(code)
                    )
                }
            }
        }
    }
}

internal fun getIconBy(symbol: String): String {
    val lowerCaseSymbol = symbol.lowercase()
    return "https://farisaziz12.github.io/cryptoicon-api/icons/$lowerCaseSymbol.png"
}
