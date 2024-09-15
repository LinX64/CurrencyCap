package ui.screens.main.overview.components.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.OverviewState.Success
import ui.screens.main.overview.components.tabs.components.CryptoGridItem

@Composable
internal fun MarketTab(
    state: OverviewState,
    onCryptoItemClick: (id: String, symbol: String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Market Overview",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        when (state) {
            is Success -> {
                val cryptoRates = state.combinedRates.crypto.sortedBy { it.marketCap }.take(4)
                Grid(
                    items = cryptoRates,
                    columns = 2,
                    modifier = Modifier.fillMaxWidth()
                ) { cryptoItem ->
                    CryptoGridItem(
                        cryptoItem = cryptoItem,
                        onCryptoItemClick = onCryptoItemClick
                    )
                }
            }

            is OverviewState.Idle -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            else -> Unit
        }
    }
}

@Composable
fun <T> Grid(
    items: List<T>,
    columns: Int,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    Column(modifier) {
        for (i in items.indices step columns) {
            Row {
                for (j in 0 until columns) {
                    if (i + j < items.size) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                        ) {
                            content(items[i + j])
                        }
                    } else {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
