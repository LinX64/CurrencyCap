package ui.screens.main.overview

import androidx.lifecycle.viewModelScope
import domain.usecase.CombineRatesNewsUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.main.overview.OverviewState.Loading
import ui.screens.main.overview.OverviewState.Success
import ui.screens.main.overview.OverviewViewEvent.OnLoadRates
import ui.screens.main.overview.OverviewViewEvent.OnRetry
import util.getIconBy

class OverviewViewModel(
    private val combinedRatesUseCase: CombineRatesNewsUseCase
) : MviViewModel<OverviewViewEvent, OverviewState, OverviewNavigationEffect>(Loading) {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        handleEvent(OnLoadRates())
    }

    override fun handleEvent(event: OverviewViewEvent) {
        when (event) {
            OnRetry -> loadCombinedRates(forceRefresh = true)
            is OnLoadRates -> loadCombinedRates(event.forceRefresh)
        }
    }

    private fun loadCombinedRates(forceRefresh: Boolean = false) {
        setState { Loading }

        viewModelScope.launch {
            combinedRatesUseCase(forceRefresh = forceRefresh)
                .map {
                    val bonbastRates = it.bonbastRates.map { bonbastRate ->
                        getIconBy(bonbastRate.code).let { bonbastRate.copy(imageUrl = it) }
                    }.toImmutableList()

                    val cryptoRates = it.cryptoRates.toImmutableList()
                    val markets = it.markets.toImmutableList()
                    val fiatRates = it.fiatRates.toImmutableList()
                    val topMovers = it.topMovers.toImmutableList()
                    val news = it.news.toImmutableList()

                    when {
                        bonbastRates.isEmpty()
                                && cryptoRates.isEmpty()
                                && markets.isEmpty()
                                && fiatRates.isEmpty()
                                && topMovers.isEmpty()
                                && news.isEmpty() -> setState { OverviewState.Error("No data available") }

                        else -> setState { Success(bonbastRates, cryptoRates, markets, fiatRates, topMovers, news) }
                    }
                }
                .launchIn(viewModelScope)
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