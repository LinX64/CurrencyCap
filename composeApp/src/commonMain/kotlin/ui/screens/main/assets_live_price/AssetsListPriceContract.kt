package ui.screens.main.assets_live_price

import domain.model.AssetPriceItem

sealed interface AssetsLivePriceViewEvent {
    data class OnSearchQueryChanged(val query: String) : AssetsLivePriceViewEvent
    data object OnFetchLivePrices : AssetsLivePriceViewEvent
}

sealed interface AssetsLivePriceState {
    data object Idle : AssetsLivePriceState
    data object Loading : AssetsLivePriceState
    data class Success(val rates: List<AssetPriceItem>) : AssetsLivePriceState
    data class Error(val message: String) : AssetsLivePriceState
}

sealed interface AssetsLivePriceNavigationEffect