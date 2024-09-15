package ui.screens.main.assets_live_price

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import di.koinViewModel
import domain.model.AssetPriceItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import ui.components.base.CenteredColumn
import ui.screens.main.assets_live_price.AssetsLivePriceState.Loading
import ui.screens.main.assets_live_price.AssetsLivePriceState.Success
import ui.screens.main.assets_live_price.AssetsLivePriceViewEvent.OnFetchLivePrices

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
    val lazyListState = rememberLazyListState()

    val displayedRates = remember(rates) { rates.take(maxItems) }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 &&
                    lastVisibleItem?.index == lazyListState.layoutInfo.totalItemsCount - 1 &&
                    displayedRates.size < maxItems &&
                    displayedRates.size % pageSize == 0
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .collectLatest { if (it) onLoadMore() }
    }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            SearchBarHeader(
                modifier = Modifier.fillMaxWidth(),
                onSearch = { /* TODO */ }
            )
        }
        itemsIndexed(
            items = displayedRates,
            key = { _, item -> item.symbol }
        ) { index, rateItem ->
            AnimatedRateRow(
                rateItem = rateItem,
                previousPrice = displayedRates.getOrNull(index - 1)?.price,
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

@Composable
fun SearchBarHeader(modifier: Modifier, onSearch: () -> Unit) {
    TextField(
        value = "",
        onValueChange = { },
        modifier = modifier,
        placeholder = { Text("Search") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            IconButton(onClick = onSearch) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear search"
                )
            }
        }
    )
}

fun String.separateCamelCase(): String {
    return this.replace(Regex("(?<=[a-z])(?=[A-Z])"), " ")
        .replaceFirstChar { it.uppercase() }
}
