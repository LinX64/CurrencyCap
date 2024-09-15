package ui.screens.main.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.HorizontalLineWithDot
import ui.components.SearchViewHeader
import ui.components.base.BaseGlassLazyColumn
import ui.screens.main.overview.components.PortfolioSection
import ui.screens.main.overview.components.TodayTopMovers
import ui.screens.main.overview.components.TopRates
import ui.screens.main.overview.components.cryptoListItems

@Composable
internal fun OverviewRoute(
    overviewViewModel: OverviewViewModel = koinViewModel(),
    hazeState: HazeState,
    onViewAllClick: () -> Unit,
    onViewAllTopRatesClick: () -> Unit,
    onSearchCardClicked: () -> Unit,
    onCircleButtonClicked: () -> Unit,
    onCryptoItemClick: (id: String, symbol: String) -> Unit,
) {
    val state by overviewViewModel.viewState.collectAsStateWithLifecycle()

    OverviewScreen(
        state = state,
        hazeState = hazeState,
        onSearchCardClicked = onSearchCardClicked,
        onCircleButtonClicked = onCircleButtonClicked,
        onViewAllClick = onViewAllClick,
        onViewAllTopRatesClick = onViewAllTopRatesClick,
        onCryptoItemClick = onCryptoItemClick,
    )
}

@Composable
internal fun OverviewScreen(
    state: OverviewState,
    hazeState: HazeState,
    onSearchCardClicked: () -> Unit,
    onCircleButtonClicked: () -> Unit,
    onViewAllClick: () -> Unit,
    onViewAllTopRatesClick: () -> Unit,
    onCryptoItemClick: (id: String, symbol: String) -> Unit,
) {
    BaseGlassLazyColumn(
        hazeState = hazeState,
    ) {
        item(key = "search_header") {
            SearchViewHeader(
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
                onCryptoItemClick = onCryptoItemClick
            )
        }

        item(key = "top_movers") {
            TodayTopMovers(overviewState = state, onCryptoItemClick = onCryptoItemClick)
        }

        item(key = "top_rates") {
            TopRates(state, onViewAllTopRatesClick)
        }

        cryptoListItems(state, onViewAllClick, onCryptoItemClick)
    }
}