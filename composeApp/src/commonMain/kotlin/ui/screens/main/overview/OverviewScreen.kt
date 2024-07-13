package ui.screens.main.overview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.ErrorView
import ui.components.HorizontalLineWithDot
import ui.components.SearchViewHeader
import ui.components.main.BaseGlassLazyColumn
import ui.screens.main.overview.OverviewViewEvent.OnRetry
import ui.screens.main.overview.components.PortfolioSection
import ui.screens.main.overview.components.TodayTopMovers
import ui.screens.main.overview.components.TopRates
import ui.screens.main.overview.components.TrendingCryptoCurrencies

@Composable
internal fun OverviewRoute(
    overviewViewModel: OverviewViewModel = koinViewModel<OverviewViewModel>(),
    padding: PaddingValues,
    hazeState: HazeState,
    onSearchCardClicked: () -> Unit,
    onNewsItemClick: (url: String) -> Unit,
    onCircleButtonClicked: () -> Unit,
    onCryptoItemClick: (symbol: String) -> Unit
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
        onRetry = { overviewViewModel.handleEvent(OnRetry) },
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
    onRetry: () -> Unit,
) {
    BaseGlassLazyColumn(
        padding = padding,
        hazeState = hazeState,
    ) {
        item {
            SearchViewHeader(
                state = state,
                onSearchCardClicked = onSearchCardClicked,
                onCircleButtonClicked = onCircleButtonClicked
            )
        }
        item { HorizontalLineWithDot() }
        item {
            PortfolioSection(
                state = state,
                onNewsItemClick = onNewsItemClick,
                onCryptoItemClick = onCryptoItemClick
            )
        }
        item { TodayTopMovers(overviewState = state, onCryptoItemClick = onCryptoItemClick) }
        item { TopRates(state) }
        item { TrendingCryptoCurrencies(overviewState = state, onCryptoItemClick) }
    }

    when (state) {
        is OverviewState.Error -> ErrorView(message = state.message, onRetry = onRetry)
        else -> Unit
    }
}

