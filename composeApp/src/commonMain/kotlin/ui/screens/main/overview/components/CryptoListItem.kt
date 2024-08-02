package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import ui.components.main.SectionRowItem
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.OverviewState.Loading
import ui.screens.main.overview.OverviewState.Success
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.getDummyCryptoItems

internal fun LazyListScope.cryptoListItems(
    overviewState: OverviewState,
    onCryptoItemClick: (String, String) -> Unit
) {
    item {
        Column {
            SectionRowItem(title = "Trending Rates")

            Spacer(modifier = Modifier.height(SPACER_PADDING_16))
        }
    }

    when (overviewState) {
        is Success -> {
            val cryptoRates = overviewState.cryptoRates.take(30)
            if (cryptoRates.isNotEmpty()) {
                items(
                    count = cryptoRates.size,
                    key = { cryptoRates[it].id }
                ) { index ->
                    CryptoHorizontalItem(
                        crypto = cryptoRates[index],
                        isLoading = false,
                        onClick = onCryptoItemClick
                    )

                    if (index < cryptoRates.size - 1) {
                        Spacer(modifier = Modifier.height(SPACER_PADDING_8))
                    }
                }
            } else {
                items(5) { index ->
                    CryptoHorizontalItem(
                        crypto = getDummyCryptoItems()[index],
                        isLoading = false,
                        onClick = onCryptoItemClick
                    )
                }
            }
        }

        is Loading -> {
            items(5) { index ->
                CryptoHorizontalItem(
                    crypto = getDummyCryptoItems()[index],
                    isLoading = true,
                    onClick = { _, _ -> }
                )
            }
        }

        else -> Unit
    }
}

