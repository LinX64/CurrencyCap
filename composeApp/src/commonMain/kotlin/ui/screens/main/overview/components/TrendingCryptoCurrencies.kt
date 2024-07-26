package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.components.main.SectionRowItem
import ui.screens.main.overview.OverviewState
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.getDummyCryptoItems

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun TrendingCryptoCurrencies(
    overviewState: OverviewState,
    onCryptoItemClick: (String) -> Unit
) {
    Column {
        SectionRowItem(
            title = "Trending Rates",
        )

        FlowColumn(
            modifier = Modifier.fillMaxWidth().padding(top = SPACER_PADDING_16),
            verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_8),
        ) {
            if (overviewState is OverviewState.Success) {
                val cryptoRates = overviewState.cryptoRates

                if (cryptoRates.isNotEmpty()) {
                    cryptoRates.forEachIndexed { index, _ -> // Takes only 50 items TODO: Add pagination
                        CryptoHorizontalItem(
                            crypto = cryptoRates[index],
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

            if (overviewState is OverviewState.Loading) {
                repeat(5) {
                    CryptoHorizontalItem(
                        crypto = getDummyCryptoItems()[it],
                        isLoading = true,
                        onClick = { /* TODO: Navigate to detail screen */ }
                    )
                }
            }
        }
    }
}
