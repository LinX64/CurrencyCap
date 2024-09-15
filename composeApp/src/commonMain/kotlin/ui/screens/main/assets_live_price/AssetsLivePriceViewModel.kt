package ui.screens.main.assets_live_price

import androidx.lifecycle.viewModelScope
import data.util.Constant.LIVE_PRICES_KEY
import domain.repository.AssetsRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import ui.common.MviViewModel
import ui.screens.main.assets_live_price.AssetsLivePriceState.Error
import ui.screens.main.assets_live_price.AssetsLivePriceState.Loading
import ui.screens.main.assets_live_price.AssetsLivePriceState.Success
import ui.screens.main.assets_live_price.AssetsLivePriceViewEvent.OnFetchLivePrices

class AssetsLivePriceViewModel(
    private val assetsRepository: AssetsRepository
) : MviViewModel<AssetsLivePriceViewEvent, AssetsLivePriceState, AssetsLivePriceNavigationEffect>(
    Loading
) {

    init {
        handleEvent(OnFetchLivePrices)
    }

    override fun handleEvent(event: AssetsLivePriceViewEvent) {
        when (event) {
            is OnFetchLivePrices -> fetchLivePrices()
        }
    }

    private fun fetchLivePrices() {
        viewModelScope.launch {
            assetsRepository.getLiveAssetRates()
                .stream(StoreReadRequest.freshWithFallBackToSourceOfTruth((LIVE_PRICES_KEY)))
                .collectLatest { response ->
                    when (response) {
                        is StoreReadResponse.Data -> {
                            val newData = response.value.sortedBy { it.symbol }
                            setState {
                                if (newData.isEmpty()) {
                                    Error("No data found")
                                } else Success(newData)
                            }
                        }

                        is StoreReadResponse.Loading -> setState { Loading }
                        is StoreReadResponse.Error -> setState {
                            Error(response.errorMessageOrNull() ?: "Error")
                        }

                        else -> Unit
                    }
                }
        }
    }
}