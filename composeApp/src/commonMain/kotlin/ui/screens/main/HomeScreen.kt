package ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import koinViewModel
import ui.screens.main.components.CenteredColumn
import ui.screens.main.components.CryptoCardItems
import ui.screens.main.components.IranianRate
import ui.screens.main.components.TrendingCryptoCurrencies

@Composable
fun HomeRoute(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
) {
    val ratesState = mainViewModel.rates.collectAsState().value
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
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            CryptoCardItems(rates)
        }
        item {
            IranianRate(rates)
        }
        item {
            TrendingCryptoCurrencies(cryptoRates)
        }
    }

    when (rates) {
        is MainState.Loading -> {
            CenteredColumn {
                CircularProgressIndicator()
            }
        }

        is MainState.Error -> {
            Text("Error: ${rates.error}")
        }

        else -> Unit
    }
}

