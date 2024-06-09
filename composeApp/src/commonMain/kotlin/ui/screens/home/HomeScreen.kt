package ui.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import di.koinViewModel
import ui.components.ErrorView
import ui.screens.home.components.CenteredColumn
import ui.screens.home.components.IranianRate
import ui.screens.home.components.MainHeader
import ui.screens.home.components.Stocks
import ui.screens.home.components.TodayTopMovers
import ui.screens.home.components.TopCrypto
import ui.screens.home.components.TrendingCryptoCurrencies

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>()
) {
    val state by mainViewModel.viewState.collectAsState()
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = padding
    ) {
        item { MainHeader() }
        item { TodayTopMovers(state) }
        item { TopCrypto(state) }
        item { IranianRate(state) }
        item { TrendingCryptoCurrencies(state) }
        item { Stocks(state) }
    }

    when (state) {
        is MainState.Loading -> CenteredColumn {
            CircularProgressIndicator()
        }

        is MainState.Error -> {
            val message = (state as MainState.Error).message
            ErrorView(message = message)
        }

        else -> Unit
    }
}


