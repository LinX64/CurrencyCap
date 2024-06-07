package ui.screens.main

import androidx.lifecycle.viewModelScope
import data.repository.MainRepository
import data.util.NetworkResult.Error
import data.util.NetworkResult.Loading
import data.util.NetworkResult.Success
import data.util.asResult
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ui.common.MviViewModel
import ui.screens.main.MainState.CryptoRatesError
import ui.screens.main.MainState.CryptoRatesSuccess
import ui.screens.main.MainViewEvent.LoadCryptoRates
import ui.screens.main.MainViewEvent.LoadIranianRates

class MainViewModel(
    private val mainRepository: MainRepository,
) : MviViewModel<MainViewEvent, MainState, NavigationEffect>(MainState.Loading) {

    init {
        handleEvent(LoadIranianRates)
        handleEvent(LoadCryptoRates)
    }

    override fun handleEvent(event: MainViewEvent) {
        when (event) {
            is LoadIranianRates -> loadIranianRates()
            is LoadCryptoRates -> loadCryptoRates()
            is MainViewEvent.RefreshRates -> {
                loadIranianRates()
                loadCryptoRates()
            }

            is MainViewEvent.ShowError -> setState {
                MainState.Error(
                    event.error.message ?: "Unknown error"
                )
            }
        }
    }

    private fun loadIranianRates() {
        mainRepository.getIranianFiat()
            .asResult()
            .map {
                when (it) {
                    is Success -> MainState.Success(it.data)
                    is Error -> MainState.Error(it.exception.message ?: "Unknown error")
                    Loading -> MainState.Loading
                }
            }
            .onEach { state -> setState { state } }
            .launchIn(viewModelScope)
    }

    private fun loadCryptoRates() {
        mainRepository.getCoinCapRates()
            .asResult()
            .map {
                when (it) {
                    is Success -> {
                        val reversed = it.data.sortedBy { it.symbol }
                        CryptoRatesSuccess(reversed)
                    }

                    is Error -> CryptoRatesError(it.exception.message ?: "Unknown error")
                    else -> MainState.Loading
                }
            }
            .onEach { state -> setState { state } }
            .launchIn(viewModelScope)
    }
}