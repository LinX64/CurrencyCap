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
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
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

    private val _chartDataState = MutableStateFlow(ChartDataUiState())
    val chartDataState = _chartDataState.asStateFlow()

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

    private fun onChartPeriodSelected(
        coinId: String,
        symbol: String,
        chipPeriod: ChipPeriod,
    ) {
        cryptoRepository.fetchMarketChartData(coinId, symbol, chipPeriod)
            .map { result ->
                when (result) {
                    is Success -> {
                        val chartData = result.data
                        if (chartData.isEmpty()) {
                            _chartDataState.value = ChartDataUiState(isLoading = false)
                        } else _chartDataState.value = ChartDataUiState(chartDataPoints = chartData.toImmutableList())
                    }

                    is Error -> _chartDataState.value = ChartDataUiState(isLoading = false)
                    is Loading -> _chartDataState.value = ChartDataUiState(isLoading = true)
                }
            }
            .launchIn(viewModelScope)
    }
}