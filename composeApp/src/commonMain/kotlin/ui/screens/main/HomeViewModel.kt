package ui.screens.main

import androidx.lifecycle.viewModelScope
import data.repository.MainRepository
import data.util.asResult
import domain.usecase.GetTopMoversUseCase
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.main.MainState.Success
import ui.screens.main.MainViewEvent.LoadRates
import ui.screens.main.MainViewEvent.RefreshRates

class MainViewModel(
    private val mainRepository: MainRepository,
    private val getTopMoversUseCase: GetTopMoversUseCase
) : MviViewModel<MainViewEvent, MainState, NavigationEffect>(MainState.Loading) {

    init {
        loadCombinedRates()
    }

    override fun handleEvent(event: MainViewEvent) = when (event) {
        is LoadRates -> loadCombinedRates()
        is RefreshRates -> loadCombinedRates()
    }

    private fun loadCombinedRates() {
        viewModelScope.launch {
            val iranianRateFlow = mainRepository.getIranianRate()
            val cryptoRatesFlow = mainRepository.getCoinCapRates()
                .map { rates ->
                    rates
                        .sortedBy { it.currencySymbol }
                        .filter { it.type == "crypto" }
                }
            val topMoversFlow = cryptoRatesFlow
                .map { rates ->
                    rates
                        .sortedByDescending { it.rateUsd }
                        .take(5)
                }
                .map {
                    it.map { topMover ->
                        TopMovers(
                            symbol = topMover.symbol ?: "",
                            rateUsd = topMover.rateUsd
                        )
                    }
                }

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
    }
}