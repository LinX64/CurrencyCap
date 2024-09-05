package ui.screens.main.overview.components.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ui.components.base.CenteredColumn
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.OverviewState.Success
import ui.screens.main.overview.components.tabs.components.CryptoGridItem
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun MarketTab(
    state: OverviewState,
    onCryptoItemClick: (id: String, symbol: String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Market Overview",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_8))

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize().height(220.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_8),
        ) {
            when (state) {
                is Success -> {
                    val cryptoRates = state.combinedRates.crypto.sortedBy { it.marketCap }.take(4)
                    items(cryptoRates.size) { item ->
                        val cryptoItem = cryptoRates[item]
                        CryptoGridItem(cryptoItem = cryptoItem, onCryptoItemClick = onCryptoItemClick)
                    }
                }

                is OverviewState.Loading -> item {
                    CenteredColumn {
                        CircularProgressIndicator()
                    }
                }

                else -> Unit
            }
        }
    }
}

