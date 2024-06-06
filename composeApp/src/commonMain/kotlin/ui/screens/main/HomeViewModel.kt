package ui.screens.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.repository.MainRepository
import data.util.NetworkResult.Error
import data.util.NetworkResult.Loading
import data.util.NetworkResult.Success
import data.util.asResult
import domain.model.DataDao
import domain.model.RateDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    mainRepository: MainRepository,
) : ViewModel() {

    val iranianRate: StateFlow<MainState> = mainRepository.getIranianFiat()
        .asResult()
        .map {
            when (it) {
                is Success -> MainState.Success(it.data)
                is Error -> MainState.Error(it.exception)
                Loading -> MainState.Loading
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainState.Loading
        )

    val cryptoRates: StateFlow<CryptoState> = mainRepository.getCoinCapRates()
        .asResult()
        .map {
            when (it) {
                is Success -> {
                    val reversed = it.data.sortedBy { it.symbol }
                    CryptoState.Success(reversed)
                }

                is Error -> CryptoState.Error(it.exception)
                Loading -> CryptoState.Loading
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CryptoState.Loading
        )
}

sealed interface MainState {
    data object Loading : MainState
    data class Success(val ratesList: List<RateDao>) : MainState
    data class Error(val error: Throwable) : MainState
}

sealed interface CryptoState {
    data object Loading : CryptoState
    data class Success(val rates: List<DataDao>) : CryptoState
    data class Error(val error: Throwable) : CryptoState
}
