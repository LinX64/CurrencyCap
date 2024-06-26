package ui.screens.overview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import di.koinViewModel
import ui.components.ErrorView
import ui.components.HorizontalLineWithDot
import ui.components.SearchViewHeader
import ui.screens.overview.components.MainHeader
import ui.screens.overview.components.TodayTopMovers
import ui.screens.overview.components.TopRates
import ui.screens.overview.components.TrendingCryptoCurrencies

@Composable
internal fun OverviewScreen(
    modifier: Modifier = Modifier,
    overviewViewModel: OverviewViewModel = koinViewModel<OverviewViewModel>(),
    padding: PaddingValues,
    hazeState: HazeState
) {
    val state by overviewViewModel.viewState.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = modifier.fillMaxSize()
            .haze(
                state = hazeState,
                style = HazeStyle(
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    blurRadius = 35.dp,
                    noiseFactor = HazeDefaults.noiseFactor
                )
            ),
        contentPadding = padding
    ) {
        item { SearchViewHeader() }
        item { HorizontalLineWithDot() }
        item { MainHeader(state) }
        item { HorizontalLineWithDot() }
        item { TodayTopMovers(state) }
        //item { WatchList(state) } // todo
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

