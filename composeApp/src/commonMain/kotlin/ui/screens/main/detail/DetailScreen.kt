package ui.screens.main.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import org.koin.core.parameter.parametersOf
import ui.components.ErrorView
import ui.components.PoweredByCoinGeckoAndCoinCapText
import ui.components.base.BaseGlassLazyColumn
import ui.screens.main.detail.DetailState.Error
import ui.screens.main.detail.DetailState.Loading
import ui.screens.main.detail.DetailState.Success
import ui.screens.main.detail.DetailViewEvent.OnChartPeriodSelect
import ui.screens.main.detail.DetailViewEvent.OnRetry
import ui.screens.main.detail.components.DescriptionCard
import ui.screens.main.detail.components.DetailBody
import ui.screens.main.detail.components.DetailHeader
import ui.theme.AppDimensions.SPACER_PADDING_16
import util.getDummyCryptoInfoItem

@Composable
internal fun DetailRoute(
    hazeState: HazeState,
    id: String,
    symbol: String,
    detailViewModel: DetailViewModel = koinViewModel { parametersOf(id, symbol) }
) {
    val state by detailViewModel.viewState.collectAsStateWithLifecycle()
    val chartDataState by detailViewModel.chartDataState.collectAsStateWithLifecycle()

    DetailScreen(
        state = state,
        chartDataState = chartDataState,
        hazeState = hazeState,
        handleEvent = detailViewModel::handleEvent
    )
}

@Composable
internal fun DetailScreen(
    state: DetailState,
    chartDataState: ChartDataUiState,
    hazeState: HazeState,
    handleEvent: (DetailViewEvent) -> Unit,
) {
    BaseGlassLazyColumn(
        hazeState = hazeState,
        verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_16),
        isEmpty = state is Error,
        emptyContent = { ErrorView(onRetry = { handleEvent(OnRetry) }) }
    ) {
        when (state) {
            is Success -> {
                val description = state.cryptoInfo.description.en
                val cryptoInfo = state.cryptoInfo
                val chartData = chartDataState.chartDataPoints

                item {
                    DetailHeader(
                        cryptoInfo = cryptoInfo,
                        chartData = chartData,
                        onChartPeriodSelect = { coinId, chipPeriod ->
                            handleEvent(OnChartPeriodSelect(coinId, cryptoInfo.symbol, chipPeriod))
                        }
                    )
                }
                item { DetailBody(cryptoInfo = cryptoInfo) }
                item { DescriptionCard(description = description) }
                item { PoweredByCoinGeckoAndCoinCapText() }
            }

            is Loading -> {
                val cryptoInfo = getDummyCryptoInfoItem()
                val description = ""

                item {
                    DetailHeader(
                        cryptoInfo = cryptoInfo,
                        isLoading = true,
                        chartData = null,
                        onChartPeriodSelect = { coinId, chipPeriod ->
                            handleEvent(OnChartPeriodSelect(coinId, cryptoInfo.symbol, chipPeriod))
                        }
                    )
                }
                item { DetailBody(cryptoInfo = cryptoInfo, isLoading = true) }
                item { DescriptionCard(description = description, isLoading = true) }
                item { PoweredByCoinGeckoAndCoinCapText() }
            }

            else -> Unit
        }
    }
}
