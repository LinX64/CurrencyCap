package ui.screens.overview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.ErrorView
import ui.components.HorizontalLineWithDot
import ui.components.SearchViewHeader
import ui.components.main.BaseBlurLazyColumn
import ui.screens.overview.components.PortfolioSection
import ui.screens.overview.components.TodayTopMovers
import ui.screens.overview.components.TopRates
import ui.screens.overview.components.TrendingCryptoCurrencies

@Composable
internal fun OverviewScreen(
    overviewViewModel: OverviewViewModel = koinViewModel<OverviewViewModel>(),
    padding: PaddingValues,
    hazeState: HazeState
) {
    val state by overviewViewModel.viewState.collectAsStateWithLifecycle()
    BaseBlurLazyColumn(
        padding = padding,
        hazeState = hazeState
    ) {
        item { SearchViewHeader(state) }
        item { HorizontalLineWithDot() }
        item { PortfolioSection(state) }
        item { HorizontalLineWithDot() }
        item { TodayTopMovers(state) }
        item { TopRates(state) }
        item { TrendingCryptoCurrencies(state) }
    }

    when (state) {
        is OverviewState.Error -> {
            val message = (state as OverviewState.Error).message
            ErrorView(message = message, onRetryClicked = { /* TODO */ })
        }

        else -> Unit
    }
}

