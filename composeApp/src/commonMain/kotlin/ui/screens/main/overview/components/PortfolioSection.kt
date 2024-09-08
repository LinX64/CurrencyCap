package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import ui.screens.main.overview.OverviewState
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun PortfolioSection(
    state: OverviewState,
    tabs: ImmutableSet<String> = persistentSetOf("Top Gainers", "Crypto", "Market"),
    onCryptoItemClick: (id: String, symbol: String) -> Unit,
) {
    val pagerState = rememberPagerState(
        pageCount = { tabs.size },
        initialPage = 1
    )

    Box {
        Row(
            modifier = Modifier
                .padding(vertical = SPACER_PADDING_16, horizontal = SPACER_PADDING_8),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HorizontalPagerTabs(
                state = state,
                pagerState = pagerState,
                onCryptoItemClick = onCryptoItemClick
            )
        }
    }
}

