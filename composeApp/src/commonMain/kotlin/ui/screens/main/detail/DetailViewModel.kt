package ui.screens.main.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import data.util.NetworkResult.Error
import data.util.NetworkResult.Loading
import data.util.NetworkResult.Success
import data.util.asResult
import domain.model.ChipPeriod
import domain.repository.CryptoRepository
import domain.repository.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import ui.common.MviViewModel
import ui.navigation.util.SYMBOL
import ui.screens.main.detail.DetailViewEvent.OnChartPeriodSelect
import ui.screens.main.detail.DetailViewEvent.OnLoadCryptoInfo
import ui.screens.main.detail.DetailViewEvent.OnRetry

class DetailViewModel(
    private val mainRepository: MainRepository,
    private val cryptoRepository: CryptoRepository,
    savedStateHandle: SavedStateHandle
) : MviViewModel<DetailViewEvent, DetailState, DetailNavigationEffect>(DetailState.Idle) {

    val symbol: String = savedStateHandle.get<String>(SYMBOL) ?: ""

    init {
        handleEvent(OnLoadCryptoInfo)
    }

    override fun handleEvent(event: DetailViewEvent) {
        when (event) {
            OnRetry -> handleEvent(OnLoadCryptoInfo)
            OnLoadCryptoInfo -> onLoadCryptoDetail()
            is OnChartPeriodSelect -> onChartPeriodSelected(event.coinId, event.coinSymbol, event.chipPeriod)
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
                        setState { DetailState.Success(crypto = crypto, cryptoInfo = info, null) }
                        handleEvent(
                            OnChartPeriodSelect(
                                coinId = result.data.first.id,
                                coinSymbol = result.data.first.symbol,
                                chipPeriod = ChipPeriod.DAY
                            )
                        )
                    }

                    is Error -> setState { DetailState.Error(result.throwable.message ?: "An error occurred") }
                    is Loading -> setState { DetailState.Loading }
                }
            }
            .launchIn(viewModelScope)
    } // TODO: Consider merging this function in one call

    private fun onChartPeriodSelected(
        coinId: String,
        coinSymbol: String,
        chipPeriod: ChipPeriod
    ) {
        val currentState = (viewState.value as? DetailState.Success) ?: return
        setState { currentState.copy(chartData = currentState.chartData?.copy(isLoading = true)) }

        cryptoRepository.fetchMarketChartData(
            coinId = coinId,
            coinSymbol = coinSymbol,
            period = chipPeriod
        )
            .asResult()
            .map { result ->
                when (result) {
                    is Success -> {
                        val chartData = result.data
                        setState {
                            currentState.copy(
                                chartData = ChartDataUiState(data = chartData.prices, isLoading = false)
                            )
                        }
                    }

                    is Error -> setState { currentState.copy(chartData = currentState.chartData?.copy(isLoading = false)) }
                    is Loading -> currentState
                }
            }
            .launchIn(viewModelScope)
    }
}