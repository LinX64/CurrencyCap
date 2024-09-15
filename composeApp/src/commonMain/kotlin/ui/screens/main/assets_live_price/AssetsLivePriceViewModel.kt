package ui.screens.main.assets_live_price

import androidx.lifecycle.viewModelScope
import data.util.Constant.LIVE_PRICES_KEY
import domain.model.AssetPriceItem
import domain.repository.AssetsRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import ui.common.MviViewModel
import ui.screens.main.assets_live_price.AssetsLivePriceState.Error
import ui.screens.main.assets_live_price.AssetsLivePriceState.Idle
import ui.screens.main.assets_live_price.AssetsLivePriceState.Success
import ui.screens.main.assets_live_price.AssetsLivePriceViewEvent.OnFetchLivePrices
import ui.screens.main.assets_live_price.AssetsLivePriceViewEvent.OnObserveSearchQuery
import ui.screens.main.assets_live_price.AssetsLivePriceViewEvent.OnSearchQueryChanged

class AssetsLivePriceViewModel(
    private val assetsRepository: AssetsRepository
) : MviViewModel<AssetsLivePriceViewEvent, AssetsLivePriceState, AssetsLivePriceNavigationEffect>(
    Idle
) {
    private var currentAssets: List<AssetPriceItem> = emptyList()
    private val searchQuery = MutableStateFlow("")
    private val _isRefreshing = MutableStateFlow(true)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        handleEvent(OnFetchLivePrices)
        handleEvent(OnObserveSearchQuery)
    }

    override fun handleEvent(event: AssetsLivePriceViewEvent) {
        when (event) {
            OnObserveSearchQuery -> observeSearchQuery()
            is OnFetchLivePrices -> fetchLivePrices()
            is OnSearchQueryChanged -> searchQuery.value = event.query
        }
    }

    private fun fetchLivePrices() {
        viewModelScope.launch {
            assetsRepository.getLiveAssetRates()
                .stream(StoreReadRequest.freshWithFallBackToSourceOfTruth((LIVE_PRICES_KEY)))
                .collectLatest { response ->
                    when (response) {
                        is StoreReadResponse.Data -> {
                            currentAssets = response.value
                            updateStateWithFilteredAssets(searchQuery.value)
                            hidePullToRefreshAfterDelay()
                        }

                        is StoreReadResponse.Loading -> _isRefreshing.value = true
                        is StoreReadResponse.Error -> {
                            setState {
                                Error(response.errorMessageOrNull() ?: "Error fetching assets")
                            }
                        }

                        else -> Unit
                    }
                }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { query ->
                    updateStateWithFilteredAssets(query)
                }
        }
    }

    fun refresh() {
        _isRefreshing.value = true

        viewModelScope.launch {
            delay(2500L)
            _isRefreshing.value = false

            handleEvent(OnFetchLivePrices)
        }
    }

    private fun updateStateWithFilteredAssets(query: String) {
        val filteredAssets = if (query.isBlank()) {
            currentAssets
        } else {
            val lowercaseQuery = query.lowercase()
            currentAssets.filter { asset ->
                asset.symbol.lowercase().contains(lowercaseQuery)
            }
        }

        setState {
            if (filteredAssets.isEmpty()) {
                Error("No matching assets found")
            } else {
                Success(filteredAssets)
            }
        }
    }

    private fun hidePullToRefreshAfterDelay() {
        viewModelScope.launch {
            delay(500L)
            _isRefreshing.value = false
        }
    }
}
