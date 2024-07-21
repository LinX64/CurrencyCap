package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
        Text(
            modifier = if (overviewState is OverviewState.Loading) getPlaceHolder(
                Modifier.padding(start = SPACER_PADDING_8),
            ) else Modifier.padding(start = SPACER_PADDING_8),
            text = "Trending rates",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        FlowColumn(
            modifier = Modifier.fillMaxWidth().padding(top = SPACER_PADDING_16),
            verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_8),
        ) {
            if (overviewState is OverviewState.Success) {
                overviewState.cryptoRates.take(50)
                    .forEachIndexed { index, rate -> // Takes only 50 items TODO: Add pagination
                        CryptoHorizontalItem(
                            crypto = overviewState.cryptoRates[index],
                            isLoading = false,
                            onClick = onCryptoItemClick
                        )
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
