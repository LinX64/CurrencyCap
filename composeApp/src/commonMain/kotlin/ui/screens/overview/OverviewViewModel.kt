package ui.screens.overview

import androidx.lifecycle.viewModelScope
import domain.model.CryptoDto
import domain.model.RateDto
import domain.repository.MainRepository
import kotlinx.coroutines.flow.first
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

            val bonbastRates = rates.mapNotNull { it.bonbast }.first()
            val cryptoRates = rates.mapNotNull { it.crypto }.first().sortedBy { it.name } // todo: use this for details maybe
            val markets = rates.mapNotNull { it.markets }.first()
            val fiatRates = rates.mapNotNull { it.rates }.first().filter { it.type == FIAT }
            val topMovers = rates.mapNotNull { it.crypto }.first().let(::mapToTopMovers)
            val cryptoFiatRates = rates.mapNotNull { it.rates }.first().let(::filterByCrypto)

            when {
                bonbastRates.isEmpty() ||
                        cryptoFiatRates.isEmpty() ||
                        markets.isEmpty() ||
                        fiatRates.isEmpty() ||
                        topMovers.isEmpty() -> setState { OverviewState.Error("Failed to load rates") }

                else -> setState {
                    Success(
                        bonbastRates = bonbastRates,
                        cryptoRates = cryptoFiatRates,
                        markets = markets,
                        fiatRates = fiatRates,
                        topMovers = topMovers
                    )
                }
            }
        }
    }

    private fun filterByCrypto(rates: List<RateDto>) = rates
        .sortedBy { it.currencySymbol }
        .filter { it.type == CRYPTO }

    private fun mapToTopMovers(rates: List<CryptoDto>) = rates
        .sortedByDescending { it.name }
        .take(4)
        .sortedBy { it.fullName }

    private fun refreshRates() {
        viewModelScope.launch {
            // todo: implement refresh logic
        }
    }
}