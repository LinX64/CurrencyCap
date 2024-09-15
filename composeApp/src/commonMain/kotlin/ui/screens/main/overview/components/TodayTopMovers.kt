package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.today_top_movers
import org.jetbrains.compose.resources.stringResource
import ui.components.main.SectionRowItem
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.OverviewState.Idle
import ui.screens.main.overview.OverviewState.Success
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.getDummyCryptoItem

@Composable
internal fun TodayTopMovers(
    overviewState: OverviewState,
    onCryptoItemClick: (id: String, symbol: String) -> Unit,
) {
    val isLoading = overviewState is Idle
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        SectionRowItem(
            title = stringResource(Res.string.today_top_movers),
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        Row(
            modifier = Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(SPACER_PADDING_8),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            when (overviewState) {
                is Success -> {
                    val topMovers = overviewState.combinedRates.crypto.sortedBy { it.name }.take(2)
                    topMovers.forEach { item ->
                        TopMoversCard(
                            isLoading = isLoading,
                            topMovers = item,
                            onClick = onCryptoItemClick,
                        )
                    }
                }

                is Idle -> {
                    repeat(2) {
                        TopMoversCard(
                            isLoading = isLoading,
                            topMovers = getDummyCryptoItem(),
                            onClick = { _, _ -> },
                        )
                    }
                }

                else -> Unit
            }
        }
    }
}


