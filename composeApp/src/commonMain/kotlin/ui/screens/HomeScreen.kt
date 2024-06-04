package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import koinViewModel
import ui.screens.components.RateHorizontalItem
import ui.screens.components.RateItem
import ui.screens.components.TopHeaderCard
import ui.theme.colors.CurrencyColors

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
    cryptoRates: MainCryptoState,
) {
    val hazeState = remember { HazeState() }
    Box(
        modifier = modifier.fillMaxSize()
            .padding(8.dp)
            .haze(
                state = hazeState,
                style = HazeDefaults.style(backgroundColor = Color.Transparent),
            )
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
        ) {
            item {
                CryptoCardItems(rates)
            }
            item {
                TopRates(rates)
            }
            item {
                TrendingRates(cryptoRates)
            }
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

@Composable
fun CryptoCardItems(
    rates: MainState
) {
    LazyHorizontalGrid(
        modifier = Modifier.fillMaxWidth()
            .heightIn(max = 180.dp),
        rows = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (rates is MainState.Success) {
            items(rates.rates.size) { index ->
                TopHeaderCard()
            }
        }
    }
}

@Composable
fun TopRates(rates: MainState) {
    Column {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = "Top rates",
            style = MaterialTheme.typography.titleLarge,
            color = CurrencyColors.White
        )

        LazyHorizontalGrid(
            modifier = Modifier.fillMaxWidth()
                .heightIn(max = 180.dp),
            rows = GridCells.Fixed(1),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (rates is MainState.Success) {
                items(rates.rates.size) { index ->
                    val code = rates.rates[index].code
                    RateItem(
                        rate = rates.rates[index],
                        icon = getIconBy(rates.rates[index].code)
                    )
                }
            }
        }
    }
}

private fun getIconBy(symbol: String): String {
    val lowerCaseSymbol = symbol.lowercase()
    return "https://farisaziz12.github.io/cryptoicon-api/icons/$lowerCaseSymbol.png"
}

@Composable
private fun TrendingRates(rates: MainCryptoState) {
    Column {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = "Trending rates",
            style = MaterialTheme.typography.titleLarge,
            color = CurrencyColors.White
        )

        LazyHorizontalGrid(
            modifier = Modifier.fillMaxWidth()
                .heightIn(max = 300.dp),
            rows = GridCells.Fixed(3),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (rates is MainCryptoState.Success) {
                items(rates.rates.size) { index ->
                    println("Crypto rate: ${rates.rates[index].symbol}")
                    RateHorizontalItem(
                        rate = rates.rates[index],
                        icon = getIconBy(rates.rates[index].symbol)
                    )
                }
            }
        }
    }
}

@Composable
fun CenteredColumn(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

