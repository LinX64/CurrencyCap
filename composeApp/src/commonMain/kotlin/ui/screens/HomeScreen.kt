package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import koinViewModel
import ui.screens.components.RateItem
import ui.screens.components.SearchBarView

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

@OptIn(ExperimentalLayoutApi::class)
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

    LazyColumn(
        modifier = modifier.fillMaxSize()
            .haze(
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
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                maxItemsInEachRow = 1,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (rates is MainState.Success) {
                    rates.rates.forEach { rate ->
                        RateItem(rate = rate)
                    }
                }
            }
        }
    }

    when (rates) {
        is MainState.Loading -> {
            CircularProgressIndicator()
        }

        is MainState.Error -> {
            Text("Error: ${rates.error}")
        }

        else -> Unit
    }
}

