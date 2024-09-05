package ui.screens.main.top_rates

import androidx.lifecycle.viewModelScope
import data.util.Constant.RATES_LIST_KEY
import domain.repository.MainRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import ui.common.MviViewModel
import ui.screens.main.top_rates.TopRatesState.Error
import ui.screens.main.top_rates.TopRatesState.Idle
import ui.screens.main.top_rates.TopRatesState.Loading
import ui.screens.main.top_rates.TopRatesState.Success
import ui.screens.main.top_rates.TopRatesViewEvent.OnGetTopRates
import ui.screens.main.top_rates.TopRatesViewEvent.OnRetry

class TopRatesViewModel(
    private val mainRepository: MainRepository,
) : MviViewModel<TopRatesViewEvent, TopRatesState, TopRatesNavigationEffect>(Idle) {

    init {
        handleEvent(OnGetTopRates)
    }

    override fun handleEvent(event: TopRatesViewEvent) {
        when (event) {
            OnGetTopRates -> fetchCryptoList()
            OnRetry -> fetchCryptoList()
        }
    }

    private fun fetchCryptoList() {
        viewModelScope.launch {
            val store = mainRepository.getAllRatesNew()
            store.stream(StoreReadRequest.cached(RATES_LIST_KEY, false))
                .collectLatest { response ->
                    when (response) {
                        is StoreReadResponse.Loading -> setState { Loading }
                        is StoreReadResponse.Data -> {
                            val rates = response.value.bonbast
                            setState { Success(rates) }
                        }

                        is StoreReadResponse.Error -> setState {
                            Error(response.errorMessageOrNull() ?: "Error")
                        }

                        else -> Unit
                    }
                }
        }
    }
}