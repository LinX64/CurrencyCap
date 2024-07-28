package ui.screens.main.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import data.util.NetworkResult.Error
import data.util.NetworkResult.Loading
import data.util.NetworkResult.Success
import data.util.asResult
import domain.model.ChartDataPoint
import domain.model.ChipPeriod
import domain.model.CoinMarketChartData
import domain.repository.CryptoRepository
import domain.repository.MainRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
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
            is OnChartPeriodSelect -> onChartPeriodSelect(event.coinId, event.chipPeriod)
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
                    }

                    is Error -> setState { DetailState.Error(result.throwable.message ?: "An error occurred") }
                    is Loading -> setState { DetailState.Loading }
                }
            }
            .launchIn(viewModelScope)
    } // TODO: Consider merging this function in one call

    private fun onChartPeriodSelect(coinId: String, chipPeriod: ChipPeriod) {
        val currentState = (viewState.value as? DetailState.Success) ?: return

        setState { currentState.copy(chartData = currentState.chartData?.copy(isLoading = true)) }

        cryptoRepository.fetchMarketChartData(coinId, chipPeriod)
            .asResult()
            .map { result ->
                when (result) {
                    is Success -> {
                        val chartData = result.data
                        setState {
                            currentState.copy(
                                chartData = ChartDataUiState(
                                    data = prepareChartData(chartData),
                                    isLoading = false
                                )
                            )
                        }
                    }

                    is Error -> setState {
                        currentState.copy(
                            chartData = currentState.chartData?.copy(isLoading = false)
                        )
                    }

                    is Loading -> currentState
                }
            }
            .launchIn(viewModelScope)
    }

    private fun prepareChartData(chartData: CoinMarketChartData): ImmutableList<ChartDataPoint> {
        return chartData.prices.map { pricePoint ->
            ChartDataPoint(
                timestamp = pricePoint.time.toEpochMilliseconds(),
                price = pricePoint.price.toFloat()
            )
        }.toImmutableList()
    }
}