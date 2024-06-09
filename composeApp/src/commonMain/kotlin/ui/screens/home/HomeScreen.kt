package ui.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
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
    val hazeState = remember { HazeState() }

    LazyColumn(
        modifier = modifier.fillMaxSize()
            .haze(
                hazeState,
                HazeStyle(
                    tint = Color.Black.copy(alpha = .2f),
                    blurRadius = 30.dp,
                    noiseFactor = HazeDefaults.noiseFactor
                )
            ),
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


