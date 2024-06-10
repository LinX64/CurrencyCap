package ui.screens.home

import androidx.lifecycle.viewModelScope
import data.util.asResult
import domain.model.DataDao
import domain.repository.MainRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.home.MainState.Success
import ui.screens.home.MainViewEvent.LoadRates
import ui.screens.home.MainViewEvent.RefreshRates

private const val CRYPTO = "crypto"

class MainViewModel(
    private val mainRepository: MainRepository
) : MviViewModel<MainViewEvent, MainState, MainNavigationEffect>(MainState.Loading) {

    init {
        loadCombinedRates()
    }

    override fun handleEvent(event: MainViewEvent) = when (event) {
        is LoadRates -> loadCombinedRates()
        is RefreshRates -> refreshRates()
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
                    cryptoRates.isEmpty() || topMovers.isEmpty() -> MainState.Loading
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