package ui.screens.main.assets_live_price

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import di.koinViewModel
import domain.model.AssetPriceItem
import ui.screens.main.assets_live_price.AssetsLivePriceState.Success
import ui.screens.main.assets_live_price.AssetsLivePriceViewEvent.OnSearchQueryChanged
import ui.screens.main.assets_live_price.components.AnimatedRateRow
import ui.screens.main.assets_live_price.components.SearchBarHeader
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
                onValueChange = { assetsLivePriceViewModel.handleEvent(OnSearchQueryChanged(it)) }
            )
        }

        else -> Unit
    }
}

@Composable
private fun AssetsLivePriceContent(
    rates: List<AssetPriceItem>,
    onValueChange: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxSize()
            .padding(SPACER_PADDING_16),
    ) {
        item(key = "search_bar") { SearchBarHeader(onValueChange = onValueChange) }
        item(key = "spacer") { Spacer(modifier = Modifier.height(SPACER_PADDING_16)) }
        items(
            count = rates.size,
            key = { index -> rates[index].symbol }
        ) { index ->
            AnimatedRateRow(
                rateItem = rates[index],
            )
        }
    }
}