package ui.screens.main.detail

import androidx.lifecycle.SavedStateHandle
import data.util.NetworkResult
import data.util.asResult
import domain.repository.MainRepository
import kotlinx.coroutines.flow.map
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
            OnLoadCryptoInfo -> onLoadCryptoDetail()
        }
    }

    private fun onLoadCryptoDetail() {
        mainRepository.getCryptoBySymbol(symbol)
            .asResult()
            .map {
                when (it) {
                    is NetworkResult.Loading -> setState { DetailState.Loading }
                    is NetworkResult.Success -> setState { DetailState.Success(it.data) }
                    is NetworkResult.Error -> setState {
                        DetailState.Error(it.throwable.message ?: "An error occurred")
                    }
                }
            }
    }
}