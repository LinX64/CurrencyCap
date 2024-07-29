package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.components.main.SectionRowItem
import ui.screens.main.overview.OverviewState
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.getDummyCryptoItems

@Composable
internal fun TrendingCryptoCurrencies(
    overviewState: OverviewState,
    onCryptoItemClick: (id: String, symbol: String) -> Unit,
) {
    Column {
        SectionRowItem(title = "Trending Rates")

        TrendingCryptoContent(overviewState, onCryptoItemClick)
    }
}

@Composable
private fun TrendingCryptoContent(
    overviewState: OverviewState,
    onCryptoItemClick: (id: String, symbol: String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = SPACER_PADDING_16),
        verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_8)
    ) {
        when (overviewState) {
            is OverviewState.Success -> {
                val cryptoRates = overviewState.cryptoRates.take(30)

                if (cryptoRates.isNotEmpty()) {
                    cryptoRates.forEach { crypto ->
                        CryptoHorizontalItem(
                            crypto = crypto,
                            isLoading = false,
                            onClick = onCryptoItemClick
                        )
                    }
                } else {
                    repeat(5) {
                        CryptoHorizontalItem(
                            crypto = getDummyCryptoItems()[it],
                            isLoading = false,
                            onClick = onCryptoItemClick
                        )
                    }
                }
            }

            is OverviewState.Loading -> {
                repeat(5) {
                    CryptoHorizontalItem(
                        crypto = getDummyCryptoItems()[it],
                        isLoading = true,
                        onClick = { _, _ -> }
                    )
                }
            }

            else -> Unit
        }
    }
}