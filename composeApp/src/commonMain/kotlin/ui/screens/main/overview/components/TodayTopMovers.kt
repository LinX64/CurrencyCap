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
import ui.screens.main.overview.OverviewState.Loading
import ui.screens.main.overview.OverviewState.Success
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.getDummyCryptoItem

@Composable
internal fun TodayTopMovers(
    overviewState: OverviewState,
    onCryptoItemClick: (id: String, symbol: String) -> Unit,
) {
    val isLoading = overviewState is Loading
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        SectionRowItem(
            isLoading = isLoading,
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
                    overviewState.topMovers.forEach { topMovers ->
                        TopMoversCard(
                            isLoading = false,
                            topMovers = topMovers,
                            onClick = onCryptoItemClick,
                        )
                    }
                }

                is Loading -> {
                    repeat(2) {
                        TopMoversCard(
                            isLoading = true,
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


