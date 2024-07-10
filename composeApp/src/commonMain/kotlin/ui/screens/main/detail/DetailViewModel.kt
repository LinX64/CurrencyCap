package ui.screens.main.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import data.util.NetworkResult.Loading
import data.util.NetworkResult.Success
import data.util.asResult
import domain.repository.MainRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.navigation.util.SYMBOL
import ui.screens.main.detail.DetailViewEvent.OnLoadCryptoInfo

class DetailViewModel(
    private val mainRepository: MainRepository,
    savedStateHandle: SavedStateHandle
) : MviViewModel<DetailViewEvent, DetailState, DetailNavigationEffect>(DetailState.Idle) {

    val symbol: String = savedStateHandle.get<String>(SYMBOL) ?: ""

    init {
        handleEvent(OnLoadCryptoInfo)
    }

    override fun handleEvent(event: DetailViewEvent) {
        when (event) {
            OnLoadCryptoInfo -> onLoadCryptoInfo()
        }
    }

    private fun onLoadCryptoInfo() {
        viewModelScope.launch {
            val name = mainRepository.getCryptoNameBySymbol(symbol).lowercase()
            val cryptoDetailFlow = mainRepository.getCryptoBySymbol(symbol).asResult()
            val cryptoInfoFlow = mainRepository.getCryptoInfoBySymbol(name).asResult()

            combine(cryptoDetailFlow, cryptoInfoFlow) { detail, info ->
                when {
                    detail is Loading || info is Loading -> DetailState.Loading
                    detail is Success && info is Success -> DetailState.Success(
                        crypto = detail.data,
                        cryptoDescription = info.data.en
                    )

                    else -> DetailState.Error("Failed to load crypto info")
                }
            }.collect { state ->
                setState { state }
            }
        }
    }
}