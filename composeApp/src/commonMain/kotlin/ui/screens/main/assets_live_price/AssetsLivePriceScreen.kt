package ui.screens.main.assets_live_price

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import di.koinViewModel
import domain.model.AssetPriceItem
import ui.components.base.CenteredColumn
import ui.screens.main.assets_live_price.AssetsLivePriceState.Loading
import ui.screens.main.assets_live_price.AssetsLivePriceState.Success
import ui.screens.main.assets_live_price.components.AnimatedRateRow
import ui.screens.main.assets_live_price.components.SearchBarHeader
import ui.theme.AppDimensions.SPACER_PADDING_16
import util.getDummyLiveRates

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
                isLoading = false,
            )
        }

        is Loading -> {
            AssetsLivePriceContent(
                rates = getDummyLiveRates(),
                isLoading = true,
            )
        }

        is Error -> CenteredColumn {
            Text(text = assetsLivePriceState.message.toString())
        }
        else -> Unit
    }
}

@Composable
private fun AssetsLivePriceContent(
    rates: List<AssetPriceItem>,
    isLoading: Boolean = false,
) {
    val lazyListState = rememberLazyListState()
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
            count = rates.size,
            key = { index -> rates[index].symbol }
        ) { index ->
            AnimatedRateRow(
                rateItem = rates[index],
                isLoading = isLoading,
            )
        }
    }
}