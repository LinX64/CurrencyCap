package ui.screens.overview

import androidx.lifecycle.viewModelScope
import domain.repository.MainRepository
import kotlinx.coroutines.flow.first
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
        setState { OverviewState.Loading }

        viewModelScope.launch {
            val rates = mainRepository.getAllRates()

            val bonbastRates = rates.map { it.bonbast }.first()
            val cryptoRates = rates.map { it.crypto }.first().filter { it.name == CRYPTO }
            val markets = rates.map { it.markets }.first()
            val fiatRates = rates.map { it.rates }.first()

            if (bonbastRates.isEmpty() || cryptoRates.isEmpty() || markets.isEmpty() || fiatRates.isEmpty()) {
                setState { OverviewState.Error("Failed to load rates") }
                return@launch
            }

            setState { Success(bonbastRates, cryptoRates, markets, fiatRates) }
        }
    }

    private fun refreshRates() {
        viewModelScope.launch {
            // todo: implement refresh logic
        }
    }
}