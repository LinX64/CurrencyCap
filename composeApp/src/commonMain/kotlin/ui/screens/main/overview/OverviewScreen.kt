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
import ui.screens.main.overview.components.PortfolioSection
import ui.screens.main.overview.components.TodayTopMovers
import ui.screens.main.overview.components.TopRates
import ui.screens.main.overview.components.TrendingCryptoCurrencies

@Composable
internal fun OverviewScreen(
    overviewViewModel: OverviewViewModel = koinViewModel<OverviewViewModel>(),
    padding: PaddingValues,
    hazeState: HazeState,
    onSearchCardClicked: () -> Unit
) {
    val state by overviewViewModel.viewState.collectAsStateWithLifecycle()
    BaseGlassLazyColumn(
        padding = padding,
        hazeState = hazeState
    ) {
        item { SearchViewHeader(state = state, onSearchCardClicked = onSearchCardClicked) }
        item { HorizontalLineWithDot() }
        item { PortfolioSection(state = state, hazeState = hazeState) }
        item { HorizontalLineWithDot() }
        item { TodayTopMovers(state) }
        item { TopRates(state) }
        item { TrendingCryptoCurrencies(state) }
    }

    when (state) {
        is OverviewState.Error -> {
            val message = (state as OverviewState.Error).message
            ErrorView(message = message, onRetry = { /* TODO */ })
        }

        else -> Unit
    }
}

