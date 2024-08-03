package ui.screens.main.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import data.util.NetworkResult.Error
import data.util.NetworkResult.Loading
import data.util.NetworkResult.Success
import domain.model.ChipPeriod
import domain.repository.CryptoRepository
import domain.repository.MainRepository
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
            OnRetry -> onLoadCryptoInfo()
            OnLoadCryptoInfo -> onLoadCryptoInfo()
            is OnChartPeriodSelect -> onChartPeriodSelected(false, event.symbol, event.chipPeriod)
        }
    }

    private fun onLoadCryptoInfo(
        forceRefresh: Boolean = false,
    ) {
        mainRepository.getCryptoInfoBySymbol(forceRefresh, id, symbol)
            .map { result ->
                when (result) {
                    is Success -> {
                        val data = result.data
                        setState { DetailState.Success(cryptoInfo = data) }
                        onChartPeriodSelected(symbol = data.symbol, chipPeriod = ChipPeriod.DAY)
                    }

                    is Error -> {
                        val cachedData = result.data
                        if (cachedData != null) setState { DetailState.Success(cryptoInfo = cachedData) }
                        else setState { DetailState.Error(result.throwable.message ?: "An error occurred") }
                    }

                    is Loading -> setState { DetailState.Loading }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun onChartPeriodSelected(
        forceRefresh: Boolean = false,
        symbol: String,
        chipPeriod: ChipPeriod,
    ) {
        cryptoRepository.fetchMarketChartData(forceRefresh, symbol, chipPeriod)
            .map { result ->
                when (result) {
                    is Success -> {
                        val chartData = result.data
                        if (chartData.isEmpty()) {
                            _chartDataState.value = ChartDataUiState(isLoading = false)
                        } else _chartDataState.value = ChartDataUiState(chartDataPoints = chartData.toSet())
                    }

                    is Error -> {
                        val cachedData = result.data
                        _chartDataState.value = ChartDataUiState(
                            chartDataPoints = cachedData?.toSet(),
                            isLoading = false,
                        )
                    }

                    is Loading -> _chartDataState.value = ChartDataUiState(isLoading = true)
                }
            }
            .launchIn(viewModelScope)
    }
}