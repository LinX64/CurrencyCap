package ui.screens.overview

import androidx.lifecycle.viewModelScope
import domain.model.RateDto
import domain.repository.MainRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.mapNotNull
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

            val bonbastRates = rates.mapNotNull { it.bonbast }.firstOrNull() ?: emptyList()
            val cryptoRates = rates.mapNotNull { it.crypto }.firstOrNull()?.sortedBy { it.name } ?: emptyList()
            val markets = rates.mapNotNull { it.markets }.firstOrNull() ?: emptyList()
            val fiatRates = rates.mapNotNull { it.rates }.firstOrNull()?.filter { it.type == FIAT } ?: emptyList()
            val topMovers = rates.mapNotNull { it.rates }.firstOrNull()?.let { mapToTopMovers(it) } ?: emptyList()

            when {
                bonbastRates.isEmpty() || cryptoRates.isEmpty() || markets.isEmpty() || fiatRates.isEmpty() || topMovers.isEmpty() -> {
                    setState { OverviewState.Error("Failed to load rates") }
                }

                else -> setState { Success(bonbastRates, cryptoRates, markets, fiatRates, topMovers) }
            }
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