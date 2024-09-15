package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.refresh
import currencycap.composeapp.generated.resources.view_all
import domain.model.main.BonbastRate
import org.jetbrains.compose.resources.stringResource
import ui.components.main.SectionRowItem
import ui.screens.main.overview.OverviewState
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun TopRates(
    rates: OverviewState,
    onViewAllClick: () -> Unit
) {
    val isLoading by remember { derivedStateOf { rates is OverviewState.Idle } }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SPACER_PADDING_16),
    ) {
        TopRatesHeader(isLoading, onViewAllClick)
        TopRatesContent(rates, isLoading)
    }
}

@Composable
private fun TopRatesHeader(
    isLoading: Boolean = false,
    onViewAllClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        SectionRowItem(
            hasSubTitle = true,
            title = "Top rates",
            subTitle = "Iranian currency",
            hasEndText = true,
            endText = stringResource(Res.string.view_all),
            onEndTextClick = onViewAllClick,
        )

        Icon(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            imageVector = Icons.Default.Refresh,
            contentDescription = stringResource(Res.string.refresh),
        )
    }
}

@Composable
private fun TopRatesContent(
    rates: OverviewState,
    isLoading: Boolean = false
) {
    val scrollableState = rememberLazyListState()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = SPACER_PADDING_8)
            .height(180.dp),
        horizontalArrangement = Arrangement.spacedBy(SPACER_PADDING_8),
        state = scrollableState,
    ) {
        when {
            rates is OverviewState.Success -> {
                val ratesList = rates.combinedRates.bonbast.take(3)
                items(ratesList.size) { index ->
                    RateItem(
                        isLoading = false,
                        icon = ratesList[index].imageUrl,
                        rate = ratesList[index]
                    )
                }
            }

            isLoading -> {
                items(5) {
                    RateItem(
                        isLoading = true,
                        icon = "",
                        rate = BonbastRate(code = "", sell = 0.0, buy = 0.0, imageUrl = "")
                    )
                }
            }
        }
    }
}