package ui.screens.main.overview

import androidx.lifecycle.viewModelScope
import data.util.NetworkResult
import domain.model.main.Crypto
import domain.repository.MainRepository
import domain.repository.NewsRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import ui.common.MviViewModel
import ui.screens.main.overview.OverviewState.Success
import ui.screens.main.overview.OverviewViewEvent.OnLoadRates
import ui.screens.main.overview.OverviewViewEvent.OnRetry

private const val FIAT = "fiat"

class OverviewViewModel(
    private val mainRepository: MainRepository,
    private val newsRepository: NewsRepository
) : MviViewModel<OverviewViewEvent, OverviewState, OverviewNavigationEffect>(OverviewState.Loading) {

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
        setState { OverviewState.Loading }

        val ratesFlow = mainRepository.getAllRates().map {
            if (it is NetworkResult.Success) it.data else null
        }.filterNotNull()

        val newsFlow = newsRepository.getNews().map {
            if (it is NetworkResult.Success) it.data else emptyList()
        }

        combine(ratesFlow, newsFlow) { rates, news ->
            val bonbastRates = rates.bonbast.toImmutableList()
            val cryptoRates = rates.crypto.sortedBy { it.name }.toImmutableList()
            val markets = rates.markets.toImmutableList()
            val fiatRates = rates.rates.filter { it.type == FIAT }.toImmutableList()
            val topMovers = rates.crypto.let(::mapToTopMovers).toImmutableList()

            when {
                bonbastRates.isEmpty() ||
                        cryptoRates.isEmpty() ||
                        markets.isEmpty() ||
                        fiatRates.isEmpty() ||
                        topMovers.isEmpty() ||
                        news.isEmpty()
                -> setState { OverviewState.Error("Failed to load rates") }

                else -> setState {
                    Success(
                        bonbastRates = bonbastRates,
                        cryptoRates = cryptoRates,
                        markets = markets,
                        fiatRates = fiatRates,
                        topMovers = topMovers,
                        news = news.toImmutableList()
                    )
                }
            }
        }
            .launchIn(viewModelScope)
    }

    private fun mapToTopMovers(rates: List<Crypto>) = rates
        .sortedByDescending { it.name }
        .take(4)
        .sortedBy { it.currentPrice }
}