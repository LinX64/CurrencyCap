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

@Composable
fun CryptoCardItems(
    mainState: MainState,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Top Cryptocurrencies",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        LazyHorizontalGrid(
            modifier = Modifier.fillMaxWidth()
                .heightIn(max = 180.dp),
            rows = GridCells.Fixed(1),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (mainState is MainState.CryptoRatesSuccess) {
                items(mainState.ratesList.size) { index ->
                    TopHeaderCard(dataDao = mainState.ratesList[index])
                }
            }
        }
    }
}