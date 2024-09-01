package ui.screens.main.overview

import androidx.lifecycle.viewModelScope
import domain.usecase.CombineRatesNewsUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.main.overview.OverviewState.Loading
import ui.screens.main.overview.OverviewState.Success
import ui.screens.main.overview.OverviewViewEvent.OnLoadRates
import ui.screens.main.overview.OverviewViewEvent.OnRetry
import util.getIconBy

class OverviewViewModel(
    private val combinedRatesUseCase: CombineRatesNewsUseCase,
) : MviViewModel<OverviewViewEvent, OverviewState, OverviewNavigationEffect>(Loading) {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        handleEvent(OnLoadRates())
    }

    override fun handleEvent(event: OverviewViewEvent) {
        when (event) {
            OnRetry -> loadCombinedRates()
            is OnLoadRates -> loadCombinedRates()
        }
    }

    private fun loadCombinedRates() {
        setState { Loading }

        viewModelScope.launch {
            val combinedRates = combinedRatesUseCase(isRefreshing.value)
            val bonbastRates = combinedRates.bonbastRates.map { bonbastRate ->
                getIconBy(bonbastRate.code).let { bonbastRate.copy(imageUrl = it) }
            }.toImmutableList()

            val cryptoRates = combinedRates.cryptoRates.toImmutableList()
            val markets = combinedRates.markets.toImmutableList()
            val fiatRates = combinedRates.fiatRates.toImmutableList()
            val topMovers = combinedRates.topMovers.toImmutableList()
            val news = combinedRates.news.toImmutableList()

            when {
                bonbastRates.isEmpty()
                        && cryptoRates.isEmpty()
                        && markets.isEmpty()
                        && fiatRates.isEmpty()
                        && topMovers.isEmpty()
                        && news.isEmpty() -> setState { OverviewState.Error("No data available") }

                else -> setState {
                    Success(
                        bonbastRates = bonbastRates,
                        cryptoRates = cryptoRates,
                        markets = markets,
                        fiatRates = fiatRates,
                        topMovers = topMovers,
                        news = news
                    )
                }
            }
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