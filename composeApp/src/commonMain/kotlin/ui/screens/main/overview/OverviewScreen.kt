package ui.screens.main.overview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.HorizontalLineWithDot
import ui.components.SearchViewHeader
import ui.components.base.BaseGlassLazyColumn
import ui.screens.main.overview.components.OptimizedTopRates
import ui.screens.main.overview.components.PortfolioSection
import ui.screens.main.overview.components.TodayTopMovers
import ui.screens.main.overview.components.TrendingCryptoCurrencies

@Composable
internal fun OverviewRoute(
    overviewViewModel: OverviewViewModel = koinViewModel<OverviewViewModel>(),
    padding: PaddingValues,
    hazeState: HazeState,
    onSearchCardClicked: () -> Unit,
    onNewsItemClick: (url: String) -> Unit,
    onCircleButtonClicked: () -> Unit,
    onCryptoItemClick: (symbol: String) -> Unit,
) {
    val state by overviewViewModel.viewState.collectAsStateWithLifecycle()

    OverviewScreen(
        padding = padding,
        state = state,
        hazeState = hazeState,
        onSearchCardClicked = onSearchCardClicked,
        onNewsItemClick = onNewsItemClick,
        onCircleButtonClicked = onCircleButtonClicked,
        onCryptoItemClick = onCryptoItemClick,
    )
}

@Composable
internal fun OverviewScreen(
    padding: PaddingValues,
    state: OverviewState,
    hazeState: HazeState,
    onSearchCardClicked: () -> Unit,
    onNewsItemClick: (url: String) -> Unit,
    onCircleButtonClicked: () -> Unit,
    onCryptoItemClick: (symbol: String) -> Unit,
) {
    BaseGlassLazyColumn(
        padding = padding,
        hazeState = hazeState,
    ) {
        item(key = "search_header") {
            SearchViewHeader(
                state = state,
                onSearchCardClicked = onSearchCardClicked,
                onCircleButtonClicked = onCircleButtonClicked
            )
        }

        item(key = "horizontal_line") {
            HorizontalLineWithDot()
        }

        item(key = "portfolio_section") {
            PortfolioSection(
                state = state,
                onNewsItemClick = onNewsItemClick,
                onCryptoItemClick = onCryptoItemClick
            )
        }

        item(key = "top_movers") {
            TodayTopMovers(overviewState = state, onCryptoItemClick = onCryptoItemClick)
        }

        item(key = "top_rates") {
            OptimizedTopRates(state)
        }

        item(key = "trending_crypto") {
            TrendingCryptoCurrencies(overviewState = state, onCryptoItemClick)
        }
    }
}