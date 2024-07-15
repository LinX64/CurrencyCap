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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import ui.screens.main.overview.OverviewState
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.getDummyCryptoItem

@Composable
internal fun TodayTopMovers(
    overviewState: OverviewState,
    onCryptoItemClick: (String) -> Unit
) {
    val isLoading = overviewState is OverviewState.Loading
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = SPACER_PADDING_8),
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
        }

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

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
                        isLoading = false,
                        onClick = onCryptoItemClick
                    )
                }
            }

            if (overviewState is OverviewState.Loading) {
                items(2) {
                    TopMoversCard(
                        isLoading = true,
                        topMovers = getDummyCryptoItem(),
                        onClick = { /* TODO */ }
                    )
                }
            }
        }
    }
}


