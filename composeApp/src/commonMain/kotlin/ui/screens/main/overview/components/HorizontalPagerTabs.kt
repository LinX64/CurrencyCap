package ui.screens.main.overview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.screens.main.overview.OverviewState
import ui.screens.main.overview.components.tabs.CryptoContent
import ui.screens.main.overview.components.tabs.MarketTab
import ui.screens.main.overview.components.tabs.NewsTab
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun HorizontalPagerTabs(
    state: OverviewState,
    pagerState: PagerState,
    onNewsItemClick: (url: String) -> Unit,
    onCryptoItemClick: (symbol: String) -> Unit
) {
    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.height(230.dp)
        ) { page ->
            when (page) {
                0 -> NewsTab(state = state, onNewsItemClick = onNewsItemClick)
                1 -> CryptoContent(state = state)
                2 -> MarketTab(state = state, onCryptoItemClick = onCryptoItemClick)
            }
        }

        HorizontalPagerDotIndicator(
            pagerState = pagerState,
            pageCount = 3
        )
    }
}

@Composable
private fun HorizontalPagerDotIndicator(
    pagerState: PagerState,
    pageCount: Int,
    activeColor: Color = Color.DarkGray,
    inactiveColor: Color = Color.LightGray,
    indicatorSize: Dp = SPACER_PADDING_8,
    spacing: Dp = SPACER_PADDING_8
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SPACER_PADDING_16),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            repeat(pageCount) { index ->
                val backgroundColor = if (pagerState.currentPage == index) inactiveColor else activeColor
                Box(
                    modifier = Modifier
                        .size(indicatorSize)
                        .clip(CircleShape)
                        .background(backgroundColor)
                )
            }
        }
    }
}

