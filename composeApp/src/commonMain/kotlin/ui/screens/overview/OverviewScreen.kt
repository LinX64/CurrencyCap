package ui.screens.overview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import di.koinViewModel
import ui.components.ErrorView
import ui.screens.overview.components.IranianRate
import ui.screens.overview.components.MainHeader
import ui.screens.overview.components.Stocks
import ui.screens.overview.components.TodayTopMovers
import ui.screens.overview.components.TopCrypto
import ui.screens.overview.components.TrendingCryptoCurrencies

@Composable
internal fun OverviewScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    overviewViewModel: OverviewViewModel = koinViewModel<OverviewViewModel>()
) {
    val state by overviewViewModel.viewState.collectAsState()
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = padding
    ) {
        item { MainHeader(state) }
        item { TodayTopMovers(state) }
        item { TopCrypto(state) }
        item { IranianRate(state) }
        item { TrendingCryptoCurrencies(state) }
        item { Stocks(state) }
    }

    when (state) {
        is OverviewState.Error -> {
            val message = (state as OverviewState.Error).message
            ErrorView(message = message)
        }

        else -> Unit
    }
}


