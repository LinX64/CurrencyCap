package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.top_five_cryptos
import currencycap.composeapp.generated.resources.view_all
import org.jetbrains.compose.resources.stringResource
import ui.components.main.SectionRowItem
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.OverviewState.Idle
import ui.screens.main.overview.OverviewState.Success
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.getDummyCryptoItems

internal fun LazyListScope.cryptoListItems(
    overviewState: OverviewState,
    onViewAllClick: () -> Unit,
    onCryptoItemClick: (String, String) -> Unit
) {
    item {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            SectionRowItem(
                title = stringResource(Res.string.top_five_cryptos),
                hasEndText = true,
                endText = stringResource(Res.string.view_all),
                onEndTextClick = onViewAllClick
            )
            Spacer(modifier = Modifier.height(SPACER_PADDING_16))
        }
    }

    when (overviewState) {
        is Success -> {
            val cryptoRates = overviewState.combinedRates.crypto.take(5)
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
                items(2) { index ->
                    CryptoHorizontalItem(
                        crypto = getDummyCryptoItems()[index],
                        isLoading = false,
                        onClick = onCryptoItemClick
                    )
                }
            }
        }

        is Idle -> {
            items(2) { index ->
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

