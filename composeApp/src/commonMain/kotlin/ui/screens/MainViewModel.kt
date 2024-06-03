package ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.repository.MainRepository
import data.util.NetworkResult
import data.util.asResult
import domain.model.RateDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    mainRepository: MainRepository
) : ViewModel() {

    val rates: StateFlow<MainState> = mainRepository.getCurrencies()
        .asResult()
        .map {
            when (it) {
                is NetworkResult.Success -> MainState.Success(it.data)
                is NetworkResult.Error -> MainState.Error(it.exception)
                NetworkResult.Loading -> MainState.Loading
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainState.Loading
        )
}

sealed interface MainState {
    data object Loading : MainState
    data class Success(val rates: List<RateDao>) : MainState
    data class Error(val error: Throwable) : MainState
}
