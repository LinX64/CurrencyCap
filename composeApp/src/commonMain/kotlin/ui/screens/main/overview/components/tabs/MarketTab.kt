package ui.screens.main.overview.components.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.model.main.Crypto
import ui.components.CenteredColumn
import ui.components.GlassCard
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.OverviewState.Success
import util.formatNumber

@Composable
internal fun MarketContent(
    state: OverviewState,
    onNewsItemClick: (url: String) -> Unit
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

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (state) {
                is Success -> {
                    val cryptoItems = state.cryptoRates
                    items(cryptoItems.size) { item ->
                        val cryptoItem = cryptoItems[item]
                        CryptoGridItem(cryptoItem)
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

@Composable
private fun CryptoGridItem(
    cryptoItem: Crypto
) {
    val randomColor = cryptoItem.name.hashCode()
    GlassCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(randomColor))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = cryptoItem.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = cryptoItem.symbol.uppercase(),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                }
            }

            Text(
                text = "$${formatNumber(cryptoItem.currentPrice)}",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "MC: $${formatNumber(cryptoItem.marketCap)}",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )
        }
    }
}
