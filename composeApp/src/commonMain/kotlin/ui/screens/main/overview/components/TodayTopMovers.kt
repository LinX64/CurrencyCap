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
import androidx.compose.ui.unit.dp
import ui.screens.main.overview.OverviewState
import util.getDummyCryptoItem

@Composable
internal fun TodayTopMovers(
    overviewState: OverviewState,
    onCryptoItemClick: (String) -> Unit
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
//
//            TextButton(
//                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
//                onClick = { /* TODO */ }
//            ) {
//                Text(
//                    text = "View All",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//            }
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


