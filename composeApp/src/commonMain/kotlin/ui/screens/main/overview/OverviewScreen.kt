package ui.screens.main.overview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    onCircleButtonClicked: () -> Unit
) {
    val state by overviewViewModel.viewState.collectAsStateWithLifecycle()

    OverviewScreen(
        padding = padding,
        state = state,
        hazeState = hazeState,
        onSearchCardClicked = onSearchCardClicked,
        onNewsItemClick = onNewsItemClick,
        onCircleButtonClicked = onCircleButtonClicked,
        onRetry = { overviewViewModel.handleEvent(OnRetry) }
    )
}

@Composable
internal fun OverviewScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    state: OverviewState,
    hazeState: HazeState,
    onSearchCardClicked: () -> Unit,
    onNewsItemClick: (url: String) -> Unit,
    onCircleButtonClicked: () -> Unit,
    onRetry: () -> Unit
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
                hazeState = hazeState,
                onNewsItemClick = onNewsItemClick
            )
        }
        item { TodayTopMovers(state) }
        item { TopRates(state) }
        item { TrendingCryptoCurrencies(state) }
    }

    when (state) {
        is OverviewState.Error -> {
            val message = state.message
            ErrorView(message = message, onRetry = onRetry)
        }

        else -> Unit
    }
}

