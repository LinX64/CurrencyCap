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
import domain.model.BonbastRateDto
import ui.screens.overview.OverviewState
import util.getIconBy

@Composable
internal fun TopRates(rates: OverviewState) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Top rates",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Iranian currency",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
            )
        }

        LazyHorizontalGrid(
            modifier = Modifier.fillMaxWidth()
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
                            BonbastRateDto(
                                code = "",
                                sell = 0,
                                buy = 0,
                            )
                        )[0]
                    )
                }
            }
        }
    }
}

