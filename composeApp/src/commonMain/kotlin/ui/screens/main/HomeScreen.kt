package ui.screens.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
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
import ui.common.ui.LoadingAnimation
import ui.screens.main.components.CryptoCardItems
import ui.screens.main.components.IranianRate
import ui.screens.main.components.MainHeader
import ui.screens.main.components.Stocks
import ui.screens.main.components.TodayTopMovers
import ui.screens.main.components.TrendingCryptoCurrencies

@Composable
fun HomeRoute(
    padding: PaddingValues,
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
) {
    val state by mainViewModel.viewState.collectAsState()
    HomeScreen(
        padding = padding,
        state = state
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    state: MainState,
) {
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
        item { TodayTopMovers() }
        item { CryptoCardItems(state) }
        item { IranianRate(state) }
        item { TrendingCryptoCurrencies(state) }
        item { Stocks(state) }
    }

    when (state) {
        is MainState.Loading -> LoadingAnimation()
        is MainState.IranianRateError -> {
            Text(text = "Error")
        }

        is MainState.CryptoRatesError -> {
            Text(text = "CryptoRatesError")
        }
        else -> Unit
    }
}


