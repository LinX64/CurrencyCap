package ui.screens.main.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import data.util.Constant.GET_CRYPTO_INFO_KEY
import domain.model.ChipPeriod
import domain.repository.CryptoRepository
import domain.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
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
            is OnChartPeriodSelect -> onChartPeriodSelected(
                false,
                id,
                event.symbol,
                event.chipPeriod
            )
        }
    }

    private fun onLoadCryptoInfo(
        forceRefresh: Boolean = false,
    ) {
        viewModelScope.launch {
            val store = mainRepository.getCryptoInfoBySymbolNew(id, symbol)
            store.stream(StoreReadRequest.cached(key = GET_CRYPTO_INFO_KEY, refresh = forceRefresh))
                .collect { response ->
                    when (response) {
                        is StoreReadResponse.Loading -> setState { DetailState.Loading }
                        is StoreReadResponse.Error -> setState { DetailState.Error("No data available") }
                        is StoreReadResponse.Data -> {
                            val data = response.value
                            setState { DetailState.Success(cryptoInfo = data) }
                        }

                        is StoreReadResponse.NoNewData -> setState { DetailState.Error("No data available") }
                        else -> Unit
                    }
                }
        }
    }

    private fun onChartPeriodSelected(
        forceRefresh: Boolean = false,
        coinId: String = id,
        symbol: String,
        chipPeriod: ChipPeriod,
    ) {
        viewModelScope.launch {
            cryptoRepository.fetchMarketChartDataNew(forceRefresh, coinId, symbol, chipPeriod)
                .stream(StoreReadRequest.cached(key = symbol, refresh = forceRefresh))
                .collectLatest { response ->
                    when (response) {
                        is StoreReadResponse.Loading -> _chartDataState.value =
                            ChartDataUiState(isLoading = true)

                        is StoreReadResponse.Error -> _chartDataState.value =
                            ChartDataUiState(isLoading = false)

                        is StoreReadResponse.Data -> {
                            val data = response.value.toSet()
                            _chartDataState.value = ChartDataUiState(chartDataPoints = data)
                        }

                        is StoreReadResponse.NoNewData -> _chartDataState.value =
                            ChartDataUiState(isLoading = false)

                        else -> Unit
                    }
                }
        }
    }
}