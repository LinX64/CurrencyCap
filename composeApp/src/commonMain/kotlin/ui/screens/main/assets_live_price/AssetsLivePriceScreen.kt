package ui.screens.main.assets_live_price

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import di.koinViewModel
import domain.model.AssetPriceItem
import kotlinx.coroutines.flow.distinctUntilChanged
import ui.components.base.CenteredColumn
import ui.screens.main.assets_live_price.AssetsLivePriceState.Loading
import ui.screens.main.assets_live_price.AssetsLivePriceState.Success
import ui.screens.main.assets_live_price.AssetsLivePriceViewEvent.OnFetchLivePrices
import ui.theme.AppDimensions.SPACER_PADDING_16

@Composable
internal fun AssetsLivePriceScreen(
    assetsLivePriceViewModel: AssetsLivePriceViewModel = koinViewModel()
) {
    val state by assetsLivePriceViewModel.viewState.collectAsStateWithLifecycle()

    when (val assetsLivePriceState = state) {
        is Success -> {
            val rates = assetsLivePriceState.rates
            AssetsLivePriceContent(
                rates = rates,
                isLoading = (state is Loading),
                onLoadMore = { assetsLivePriceViewModel.handleEvent(OnFetchLivePrices) }
            )
        }

        is Loading -> CenteredColumn { CircularProgressIndicator() }
        else -> Unit
    }
}

@Composable
fun AssetsLivePriceContent(
    rates: List<AssetPriceItem>,
    isLoading: Boolean = false,
    pageSize: Int = 20,
    maxItems: Int = 100,
    onLoadMore: () -> Unit
) {
    val lazyListState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    val (displayedRates, shouldLoadMore) = remember(rates, maxItems, pageSize) {
        val displayed = rates.take(maxItems)
        val shouldLoad = displayed.size < maxItems && displayed.size % pageSize == 0
        displayed to shouldLoad
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .collect { lastIndex ->
                if (lastIndex != null && lastIndex >= displayedRates.lastIndex && shouldLoadMore) {
                    onLoadMore()
                }
            }
    }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxSize()
            .padding(SPACER_PADDING_16),
    ) {
        item {
            SearchBarHeader(
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { /* TODO */ }
            )
        }

        item { Spacer(modifier = Modifier.height(SPACER_PADDING_16)) }

        items(
            count = displayedRates.size,
            key = { index -> displayedRates[index].symbol }
        ) { index ->
            AnimatedRateRow(
                rateItem = displayedRates[index],
                isLoading = isLoading
            )
        }

        if (isLoading && displayedRates.isNotEmpty() && displayedRates.size < maxItems) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        if (displayedRates.size >= maxItems) {
            item {
                Text(
                    text = "Maximum number of items reached",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}