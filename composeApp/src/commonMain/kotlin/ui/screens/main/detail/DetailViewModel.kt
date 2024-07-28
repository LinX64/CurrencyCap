package ui.screens.main.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import data.util.NetworkResult.Error
import data.util.NetworkResult.Loading
import data.util.NetworkResult.Success
import data.util.asResult
import domain.repository.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
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

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun onLoadCryptoDetail() {
        mainRepository.getCryptoBySymbol(symbol)
            .flatMapLatest { crypto ->
                mainRepository.getCryptoInfoById(crypto.id)
                    .map { info -> Pair(crypto, info) }
            }
            .asResult()
            .map { result ->
                when (result) {
                    is Success -> {
                        val (crypto, info) = result.data
                        setState {
                            DetailState.Success(
                                crypto = crypto,
                                description = info.description.en
                            )
                        }
                    }

                    is Error -> setState { DetailState.Error(result.throwable.message ?: "An error occurred") }
                    is Loading -> setState { DetailState.Loading }
                }
            }
            .launchIn(viewModelScope)
    }
}