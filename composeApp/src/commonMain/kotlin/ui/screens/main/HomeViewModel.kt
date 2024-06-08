package ui.screens.main

import androidx.lifecycle.viewModelScope
import data.repository.MainRepository
import data.util.asResult
import domain.model.DataDao
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.main.MainState.Success
import ui.screens.main.MainViewEvent.LoadRates
import ui.screens.main.MainViewEvent.RefreshRates

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
            setState { Success(iranianRate, cryptoRates, topMovers) }
        }
            .asResult()
            .launchIn(viewModelScope)
    }
    // todo check this later

    private fun refreshRates() {
        viewModelScope.launch {
            // todo: implement refresh logic
        }
    }

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
}