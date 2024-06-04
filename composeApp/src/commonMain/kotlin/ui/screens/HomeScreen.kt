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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import koinViewModel
import ui.screens.components.RateHorizontalItem
import ui.screens.components.RateItem
import ui.screens.components.SearchBarView
import ui.theme.colors.CurrencyColors

@Composable
fun HomeRoute(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
    searchViewModel: SearchViewModel = koinViewModel<SearchViewModel>(),
) {
    val ratesState = mainViewModel.rates.collectAsState().value
    val searchResultState = searchViewModel.searchResultState.collectAsState().value
    HomeScreen(
        rates = ratesState,
        searchResultState = searchResultState,
        onSearchClick = searchViewModel::onSearchClick,
        onClear = searchViewModel::onClear,
        onItemClick = {}
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    rates: MainState,
    searchResultState: SearchUiState,
    onSearchClick: (String) -> Unit = {},
    onClear: () -> Unit,
    onItemClick: (String) -> Unit
) {
    val shouldShowDefaultContent = remember { mutableStateOf(true) }
    val hazeState = remember { HazeState() }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier.haze(
                state = hazeState,
                style = HazeStyle(
                    HazeDefaults.tint(Color.Transparent),
                    HazeDefaults.blurRadius,
                    HazeDefaults.noiseFactor
                )
            )
        ) {
            item {
                SearchBarView(
                    searchUiState = searchResultState,
                    isSearchBarActive = { shouldShowDefaultContent.value = !it },
                    onSearchClick = onSearchClick,
                    onClear = onClear,
                    onItemClick = onItemClick
                )
            }
            item {
                TopRates(rates)
            }
            item {
                TrendingRates(rates)
            }
            item {
                TrendingRates(rates)
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
private fun TopRates(rates: MainState) {
    Column {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = "Top rates",
            style = MaterialTheme.typography.titleLarge,
            color = CurrencyColors.White
        )

        LazyHorizontalGrid(
            modifier = Modifier.fillMaxWidth()
                .heightIn(max = 250.dp),
            rows = GridCells.Fixed(1),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (rates is MainState.Success) {
                items(rates.rates.size) { index ->
                    RateItem(rate = rates.rates[index])
                }
            }
        }
    }
}

@Composable
private fun TrendingRates(rates: MainState) {
    Column {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = "Trending rates",
            style = MaterialTheme.typography.titleLarge,
            color = CurrencyColors.White
        )

        LazyHorizontalGrid(
            modifier = Modifier.fillMaxWidth()
                .heightIn(max = 350.dp),
            rows = GridCells.Fixed(3),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (rates is MainState.Success) {
                items(rates.rates.size) { index ->
                    RateHorizontalItem(rate = rates.rates[index])
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

