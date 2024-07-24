package ui.screens.main.overview.components

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
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.refresh
import domain.model.main.BonbastRate
import org.jetbrains.compose.resources.stringResource
import ui.screens.main.overview.OverviewState
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.getIconBy

@Composable
internal fun TopRates(rates: OverviewState) {
    val isLoading = rates is OverviewState.Loading

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = SPACER_PADDING_8, vertical = SPACER_PADDING_16),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TopRatesTitleSection(isLoading)

            Icon(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(Res.string.refresh),
            )
        }

        LazyHorizontalGrid(
            modifier = Modifier.fillMaxWidth()
                .padding(top = SPACER_PADDING_8)
                .heightIn(max = 180.dp),
            rows = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(SPACER_PADDING_8)
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

