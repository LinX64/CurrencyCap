package ui.screens.main

import androidx.lifecycle.viewModelScope
import data.repository.MainRepository
import data.util.asResult
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.main.MainViewEvent.LoadRates
import ui.screens.main.MainViewEvent.RefreshRates

class MainViewModel(
    private val mainRepository: MainRepository,
) : MviViewModel<MainViewEvent, MainState, NavigationEffect>(MainState.Loading) {

    init {
        handleEvent(LoadRates)
    }

    override fun handleEvent(event: MainViewEvent) = when (event) {
        is LoadRates -> loadCombinedRates()
        is RefreshRates -> loadCombinedRates()
    }

    private fun loadCombinedRates() {
        viewModelScope.launch {
            val iranianRateFlow = mainRepository.getIranianRate()
            val cryptoRatesFlow = mainRepository.getCoinCapRates()

            combine(iranianRateFlow, cryptoRatesFlow) { iranianRate, cryptoRates ->
                setState { MainState.Success(iranianRate, cryptoRates) }
            }
                .asResult()
                .launchIn(viewModelScope)
        }
    }
}