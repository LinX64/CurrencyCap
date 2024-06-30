package com.client.currencycap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import domain.model.main.Crypto
import ui.components.CenteredColumn
import ui.screens.main.overview.components.TopMoversCard

@Composable
@Preview(showBackground = true)
private fun ExchangePreview() {
    val hazeState = remember { HazeState() }

    KoinPreview {
        CenteredColumn {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
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
}