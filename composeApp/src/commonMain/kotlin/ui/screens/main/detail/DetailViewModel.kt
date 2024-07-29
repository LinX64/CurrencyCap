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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import ui.common.MviViewModel
import ui.navigation.util.ID
import ui.screens.main.detail.DetailViewEvent.OnChartPeriodSelect
import ui.screens.main.detail.DetailViewEvent.OnLoadCryptoInfo
import ui.screens.main.detail.DetailViewEvent.OnRetry

class DetailViewModel(
    private val mainRepository: MainRepository,
    private val cryptoRepository: CryptoRepository,
    savedStateHandle: SavedStateHandle
) : MviViewModel<DetailViewEvent, DetailState, DetailNavigationEffect>(DetailState.Idle) {

    val id: String = savedStateHandle.get<String>(ID) ?: ""

    init {
        handleEvent(OnLoadCryptoInfo)
    }

    override fun handleEvent(event: DetailViewEvent) {
        when (event) {
            OnRetry -> handleEvent(OnLoadCryptoInfo)
            OnLoadCryptoInfo -> onLoadCryptoDetail()
            is OnChartPeriodSelect -> onChartPeriodSelected(event.coinId, event.chipPeriod)
        }
    }

    private fun onLoadCryptoDetail() {
        mainRepository.getCryptoInfoById(id)
            .asResult()
            .map { result ->
                when (result) {
                    is Success -> {
                        val data = result.data
                        setState { DetailState.Success(cryptoInfo = data) }
                        handleEvent(OnChartPeriodSelect(coinId = id, chipPeriod = ChipPeriod.DAY))
                    }

                    is Error -> setState { DetailState.Error(result.throwable.message ?: "An error occurred") }
                    is Loading -> setState { DetailState.Loading }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun onChartPeriodSelected(
        coinId: String,
        chipPeriod: ChipPeriod,
    ) {
        val currentState = (viewState.value as? DetailState.Success) ?: return
        setState { currentState.copy(chartData = currentState.chartData?.copy(isLoading = true)) }

        cryptoRepository.fetchMarketChartData(
            coinId = coinId,
            period = chipPeriod
        )
            .asResult()
            .map { result ->
                when (result) {
                    is Success -> {
                        val chartData = result.data
                        setState {
                            currentState.copy(chartData = ChartDataUiState(data = chartData.prices, isLoading = false))
                        }
                    }

                    is Error -> setState { currentState.copy(chartData = currentState.chartData?.copy(isLoading = false)) }
                    is Loading -> currentState
                }
            }
            .launchIn(viewModelScope)
    }
}