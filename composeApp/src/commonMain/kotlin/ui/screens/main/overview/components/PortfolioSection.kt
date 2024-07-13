package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.components.main.VerticalBarCard
import ui.screens.main.overview.OverviewState

@Composable
internal fun PortfolioSection(
    state: OverviewState,
    usd: String = "USD",
    hazeState: HazeState,
    scope: CoroutineScope = rememberCoroutineScope(),
    tabs: List<String> = listOf("News", "Crypto", "Market"),
    onNewsItemClick: (url: String) -> Unit,
    onCryptoItemClick: (symbol: String) -> Unit
) {
    val isLoading = state is OverviewState.Loading
    val pagerState = rememberPagerState(
        pageCount = { tabs.size },
        initialPage = 1
    )

    Box {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HorizontalPagerTabs(
                modifier = Modifier.weight(1f),
                isLoading = isLoading,
                state = state,
                pagerState = pagerState,
                tabs = tabs,
                onNewsItemClick = onNewsItemClick,
                onCryptoItemClick = onCryptoItemClick
            )
        }

        VerticalBarCard(
            modifier = Modifier.align(Alignment.TopEnd),
            onTabSelected = { index ->
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }
        )
    }
}

