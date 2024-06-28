package ui.screens.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.model.main.BonbastRate
import ui.screens.overview.OverviewState
import util.getIconBy

@Composable
internal fun TopRates(rates: OverviewState) {
    val isLoading = rates is OverviewState.Loading

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TopRatesTitleSection(isLoading)

            Icon(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
            )
        }

        LazyHorizontalGrid(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 16.dp)
                .heightIn(max = 180.dp),
            rows = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (rates is OverviewState.Success) {
                items(rates.bonbastRates.size) { index ->
                    val code = rates.bonbastRates[index].code
                    RateItem(
                        rate = rates.bonbastRates[index],
                        icon = getIconBy(code)
                    )
                }
            }

            if (rates is OverviewState.Loading) {
                items(5) {
                    RateItem(
                        isLoading = true,
                        icon = "",
                        rate = listOf(
                            BonbastRate(
                                code = "",
                                sell = 0.0,
                                buy = 0.0,
                            )
                        )[0]
                    )
                }
            }
        }
    }
}

@Composable
private fun TopRatesTitleSection(isLoading: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = "Top rates",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = "Iranian currency",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

