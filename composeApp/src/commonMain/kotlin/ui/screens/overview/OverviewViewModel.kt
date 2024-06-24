package ui.screens.overview

import androidx.lifecycle.viewModelScope
import domain.model.RateDto
import domain.repository.MainRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.overview.OverviewState.Success
import ui.screens.overview.OverviewViewEvent.OnLoadRates
import ui.screens.overview.OverviewViewEvent.OnRefreshRates

private const val CRYPTO = "crypto"
private const val FIAT = "fiat"

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
        setState { OverviewState.Loading }
        viewModelScope.launch {
            val rates = mainRepository.getAllRates()

            val bonbastRates = rates.map { it.bonbast }.first()
            val cryptoRates = rates.map { it.crypto }.first().sortedBy { it.name }
            val markets = rates.map { it.markets }.first()
            val fiatRates = rates.map { it.rates }.first().filter { it.type == FIAT }
            val topMovers = mapToTopMovers(filterByCrypto(fiatRates))

            if (bonbastRates.isEmpty() || cryptoRates.isEmpty() || markets.isEmpty() || fiatRates.isEmpty()) {
                setState { OverviewState.Error("Failed to load rates") }
                return@launch
            }

            setState { Success(bonbastRates, cryptoRates, markets, fiatRates, topMovers) }
        }
    }

    private fun filterByCrypto(rates: List<RateDto>) = rates
        .sortedBy { it.currencySymbol }
        .filter { it.type == CRYPTO }

    private fun mapToTopMovers(rates: List<RateDto>) = rates
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