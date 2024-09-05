package ui.screens.main.overview

import androidx.lifecycle.viewModelScope
import data.util.Constant.ALL_RATES_KEY
import domain.repository.MainRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import ui.common.MviViewModel
import ui.screens.main.overview.OverviewState.Loading
import ui.screens.main.overview.OverviewState.Success
import ui.screens.main.overview.OverviewViewEvent.OnLoadRates
import ui.screens.main.overview.OverviewViewEvent.OnRetry

class OverviewViewModel(
    private val mainRepository: MainRepository,
) : MviViewModel<OverviewViewEvent, OverviewState, OverviewNavigationEffect>(Loading) {

    private val _isRefreshing = MutableStateFlow(true)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    override fun handleEvent(event: OverviewViewEvent) {
        when (event) {
            OnRetry -> loadCombinedRates()
            is OnLoadRates -> loadCombinedRates(event.forceRefresh)
        }
    }

    private fun loadCombinedRates(onForceRefresh: Boolean = false) {
        setState { Loading }
        _isRefreshing.value = true

        viewModelScope.launch {
            mainRepository.getAllRatesNew()
                .stream(StoreReadRequest.cached(ALL_RATES_KEY, onForceRefresh))
                .collectLatest { response ->
                    when (response) {
                        is StoreReadResponse.Data -> {
                            val data = response.value
                            setState { Success(data) }
                            hidePullToRefreshAfterDelay()
                        }

                        is StoreReadResponse.Error -> {
                            setState {
                                OverviewState.Error(
                                    response.errorMessageOrNull() ?: "Unknown error"
                                )
                            }
                            hidePullToRefreshAfterDelay()
                        }

                        else -> Unit
                    }
                }
        }
    }

    private fun hidePullToRefreshAfterDelay() {
        viewModelScope.launch {
            delay(500L)
            _isRefreshing.value = false
        }
    }

    fun refresh() {
        setState { Loading }
        _isRefreshing.value = true

        viewModelScope.launch {
            delay(2500L)
            _isRefreshing.value = false

            handleEvent(OnLoadRates(forceRefresh = true))
        }
    }
}