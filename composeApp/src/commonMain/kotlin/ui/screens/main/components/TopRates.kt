package ui.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.screens.main.MainState
import ui.theme.colors.CurrencyColors

@Composable
fun IranianRate(rates: MainState) {
    Column {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = "Top rates",
            style = MaterialTheme.typography.titleLarge,
            color = CurrencyColors.White
        )

        LazyHorizontalGrid(
            modifier = Modifier.fillMaxWidth()
                .heightIn(max = 180.dp),
            rows = GridCells.Fixed(1),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (rates is MainState.Success) {
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
