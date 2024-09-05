package ui.screens.main.top_rates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import di.koinViewModel
import domain.model.main.BonbastRate
import ui.screens.main.top_rates.TopRatesState.Loading
import ui.screens.main.top_rates.TopRatesState.Success
import ui.screens.main.top_rates.TopRatesViewEvent.OnRetry
import ui.screens.main.top_rates.components.RateHorizontalItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopRatesRoute(
    topRatesViewModel: TopRatesViewModel = koinViewModel(),
) {
    val state by topRatesViewModel.viewState.collectAsStateWithLifecycle()
    val pullToRefreshState = rememberPullToRefreshState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (state) {
                is Success -> {
                    val topRates = (state as Success).topRates
                    items(
                        count = topRates.size,
                        key = { index -> topRates[index].code }
                    ) { index ->
                        RateHorizontalItem(
                            icon = topRates[index].imageUrl,
                            rate = topRates[index]
                        )
                    }
                }

                is Loading -> {
                    items(10) {
                        RateHorizontalItem(
                            isLoading = true,
                            icon = "",
                            rate = BonbastRate(code = "", sell = 0.0, buy = 0.0, imageUrl = "")
                        )
                    }
                }

                is Error -> {
                    item {
                        ErrorMessage(
                            message = (state as Error).message ?: "An error occurred",
                            onRetry = { topRatesViewModel.handleEvent(OnRetry) }
                        )
                    }
                }

                else -> Unit
            }
        }

//        PullToRefreshDefaults.Indicator(
//            state = pullToRefreshState,
//            isRefreshing = state is Loading,
//            color = MaterialTheme.colorScheme.primary,
//        )
        // TODO: See if it is possible to implement PTR in each screen individually
    }
}

@Composable
private fun ErrorMessage(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(text = "Retry")
        }
    }
}