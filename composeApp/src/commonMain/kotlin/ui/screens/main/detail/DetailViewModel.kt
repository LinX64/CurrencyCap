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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ui.common.MviViewModel
import ui.navigation.util.ID
import ui.navigation.util.SYMBOL
import ui.screens.main.detail.DetailViewEvent.OnChartPeriodSelect
import ui.screens.main.detail.DetailViewEvent.OnLoadCryptoInfo
import ui.screens.main.detail.DetailViewEvent.OnRetry

class DetailViewModel(
    private val mainRepository: MainRepository,
    private val cryptoRepository: CryptoRepository,
    savedStateHandle: SavedStateHandle
) : MviViewModel<DetailViewEvent, DetailState, DetailNavigationEffect>(DetailState.Idle) {

    val id: String = savedStateHandle.get<String>(ID) ?: ""
    val symbol: String = savedStateHandle.get<String>(SYMBOL) ?: ""

    init {
        handleEvent(OnLoadCryptoInfo)
    }

    override fun handleEvent(event: DetailViewEvent) {
        when (event) {
            OnRetry -> handleEvent(OnLoadCryptoInfo)
            OnLoadCryptoInfo -> onLoadCryptoInfo()
            is OnChartPeriodSelect -> onChartPeriodSelected(event.coinId, event.symbol, event.chipPeriod)
        }
    }

    private fun onLoadCryptoInfo() {
        mainRepository.getCryptoInfoById(id)
            .asResult()
            .map { result ->
                when (result) {
                    is Success -> {
                        val data = result.data
                        setState { DetailState.Success(cryptoInfo = data) }
                        onChartPeriodSelected(coinId = data.id, symbol = data.symbol, chipPeriod = ChipPeriod.DAY)
                    }

                    is Error -> setState { DetailState.Error(result.throwable.message ?: "An error occurred") }
                    is Loading -> setState { DetailState.Loading }
                }
            }
            .launchIn(viewModelScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun onChartPeriodSelected(
        coinId: String,
        symbol: String,
        chipPeriod: ChipPeriod,
    ) {
        cryptoRepository.fetchMarketChartData(
            coinId = coinId,
            symbol = symbol,
            period = chipPeriod
        )
            .asResult()
            .mapLatest { result ->
                when (result) {
                    is Success -> {
                        val chartData = result.data.prices
                        //setState { DetailState.Success(chartData = ChartDataUiState(chartDataPoints = chartData)) }
                    }

                    is Error -> setState { DetailState.Error(result.throwable.message ?: "An error occurred") }
                    is Loading -> {
                        // setState { DetailState.Success(chartData = ChartDataUiState(isLoading = true)) }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}