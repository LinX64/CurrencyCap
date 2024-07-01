package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.model.main.Crypto
import ui.screens.main.overview.OverviewState

@Composable
internal fun TodayTopMovers(
    overviewState: OverviewState
) {
    val isLoading = overviewState is OverviewState.Loading
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = "Today's Top Movers",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            TextButton(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                onClick = { /* TODO */ }
            ) {
                Text(
                    text = "View All",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (overviewState is OverviewState.Success) {
                items(2) {
                    val topMovers = overviewState.topMovers[it]
                    TopMoversCard(
                        topMovers = topMovers,
                        isLoading = false
                    )
                }
            }

            if (overviewState is OverviewState.Loading) {
                items(2) {
                    TopMoversCard(
                        isLoading = true,
                        topMovers = Crypto(
                            ath = 128.129,
                            athChangePercentage = 130.131,
                            athDate = "mea",
                            atl = 132.133,
                            atlChangePercentage = 134.135,
                            atlDate = "vix",
                            circulatingSupply = 136.137,
                            currentPrice = 138.139,
                            fullyDilutedValuation = null,
                            high24h = 140.141,
                            id = "assueverit",
                            image = "aliquam",
                            lastUpdated = "civibus",
                            low24h = 142.143,
                            marketCap = 4527,
                            marketCapChange24h = 144.145,
                            marketCapChangePercentage24h = 146.147,
                            marketCapRank = 2193,
                            maxSupply = null,
                            name = "Cheri Wright",
                            priceChange24h = 148.149,
                            priceChangePercentage24h = 150.151,
                            symbol = "veri",
                            totalSupply = null,
                            totalVolume = 152.153
                        )
                    )
                }
            }
        }
    }
}


