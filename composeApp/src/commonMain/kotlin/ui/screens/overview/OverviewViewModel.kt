package ui.screens.overview

import androidx.lifecycle.viewModelScope
import data.util.asResult
import domain.model.DataDao
import domain.repository.MainRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.overview.OverviewState.Success
import ui.screens.overview.OverviewViewEvent.OnLoadRates
import ui.screens.overview.OverviewViewEvent.OnRefreshRates

private const val CRYPTO = "crypto"

class OverviewViewModel(
    private val mainRepository: MainRepository
) : MviViewModel<OverviewViewEvent, OverviewState, OverviewNavigationEffect>(OverviewState.Loading) {

    init {
        loadCombinedRates()
    }

    override fun handleEvent(event: OverviewViewEvent) = when (event) {
        is OnLoadRates -> loadCombinedRates()
        is OnRefreshRates -> refreshRates()
    }

    private fun loadCombinedRates() {
        val iranianRateFlow = mainRepository.getIranianRate()
        val cryptoRatesFlow = mainRepository.getCoinCapRates().map(::filterByCrypto)
        val topMoversFlow = cryptoRatesFlow.map(::mapToTopMovers)

        combine(
            iranianRateFlow,
            cryptoRatesFlow,
            topMoversFlow
        ) { iranianRate, cryptoRates, topMovers ->
            setState {
                when {
                    iranianRate.isEmpty() || cryptoRates.isEmpty() || topMovers.isEmpty() -> OverviewState.Loading
                    else -> Success(
                        iranianRate = iranianRate,
                        topMovers = topMovers,
                        cryptoRates = cryptoRates
                    )
                }
            }
        }
            .asResult()
            .launchIn(viewModelScope)
    }
    // TODO: fix me

    private fun filterByCrypto(rates: List<DataDao>) = rates
        .sortedBy { it.currencySymbol }
        .filter { it.type == CRYPTO }

    private fun mapToTopMovers(rates: List<DataDao>) = rates
        .sortedByDescending { it.rateUsd }
        .take(4)
        .map { topMover ->
            TopMovers(
                symbol = topMover.symbol,
                rateUsd = topMover.rateUsd
            )
        }

    private fun refreshRates() {
        viewModelScope.launch {
            // todo: implement refresh logic
        }
    }
}