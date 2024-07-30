package ui.screens.main.overview

import androidx.lifecycle.viewModelScope
import com.mvicompose.linx64.ui.MviViewModel
import domain.usecase.CombineRatesNewsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.screens.main.overview.OverviewState.Loading
import ui.screens.main.overview.OverviewState.Success
import ui.screens.main.overview.OverviewViewEvent.OnLoadRates
import ui.screens.main.overview.OverviewViewEvent.OnRetry

class OverviewViewModel(
    private val combinedRatesUseCase: CombineRatesNewsUseCase
) : MviViewModel<OverviewViewEvent, OverviewState, OverviewNavigationEffect>(Loading) {

    init {
        handleEvent(OnLoadRates)
    }

    override fun handleEvent(event: OverviewViewEvent) {
        when (event) {
            OnRetry -> loadCombinedRates()
            is OnLoadRates -> loadCombinedRates()
        }
    }

    private fun loadCombinedRates() {
        setState { Loading }

        viewModelScope.launch {
            combinedRatesUseCase()
                .map {
                    val bonbastRates = it.bonbastRates
                    val cryptoRates = it.cryptoRates
                    val markets = it.markets
                    val fiatRates = it.fiatRates
                    val topMovers = it.topMovers
                    val news = it.news

                    when {
                        bonbastRates.isEmpty()
                                && cryptoRates.isEmpty()
                                && markets.isEmpty()
                                && fiatRates.isEmpty()
                                && topMovers.isEmpty()
                                && news.isEmpty() -> setState { OverviewState.Error("No data available") }

                        else -> setState { Success(bonbastRates, cryptoRates, markets, fiatRates, topMovers, news) }
                    }
                }
                .launchIn(viewModelScope)
        }
    }
}