package ui.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.screens.main.MainState

@Composable
fun CryptoCardItems(
    rates: MainState
) {
    LazyHorizontalGrid(
        modifier = Modifier.fillMaxWidth()
            .heightIn(max = 180.dp),
        rows = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (rates is MainState.Success) {
            items(rates.ratesList.size) { index ->
                TopHeaderCard()
            }
        }
    }
}