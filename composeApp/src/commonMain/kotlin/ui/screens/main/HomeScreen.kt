package ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import di.koinViewModel
import ui.screens.main.components.CenteredColumn
import ui.screens.main.components.CryptoCardItems
import ui.screens.main.components.IranianRate
import ui.screens.main.components.MainHeader
import ui.screens.main.components.Stocks
import ui.screens.main.components.TodayTopMovers
import ui.screens.main.components.TrendingCryptoCurrencies

@Composable
fun HomeRoute(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
) {
    val ratesState = mainViewModel.iranianRate.collectAsState().value
    val cryptoRates = mainViewModel.cryptoRates.collectAsState().value

    HomeScreen(
        rates = ratesState,
        cryptoRates = cryptoRates
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    rates: MainState,
    cryptoRates: CryptoState,
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
            )
    ) {
        item { MainHeader() }
        item { TodayTopMovers() }
        item { CryptoCardItems(cryptoRates) }
        item { IranianRate(rates) }
        item { TrendingCryptoCurrencies(cryptoRates) }
        item { Stocks(cryptoRates) }
    }

    when (rates) {
        is MainState.Loading -> CenteredColumn {
            CircularProgressIndicator()
        }

        is MainState.Error -> Text("Error: ${rates.error}")
        else -> Unit
    }
}


