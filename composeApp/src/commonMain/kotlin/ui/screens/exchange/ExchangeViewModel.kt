package ui.screens.exchange

import androidx.lifecycle.viewModelScope
import data.repository.MainRepository
import data.util.NetworkResult
import data.util.asResult
import domain.model.DataDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import ui.common.MviViewModel
import ui.screens.exchange.ExchangeState.Error
import ui.screens.exchange.ExchangeState.Success
import ui.screens.exchange.ExchangeViewEvent.OnAmountChange
import ui.screens.exchange.ExchangeViewEvent.OnConvertClick
import ui.screens.exchange.ExchangeViewEvent.OnFromChange
import ui.screens.exchange.ExchangeViewEvent.OnToChange

private const val FIAT = "fiat"

internal class ExchangeViewModel(
    private val mainRepository: MainRepository
) : MviViewModel<ExchangeViewEvent, ExchangeState, ExchangeNavigationEffect>(ExchangeState.Loading) {

    private val fromValue = MutableStateFlow("")
    private val toValue = MutableStateFlow("")
    val amountValue = MutableStateFlow("")
    val convertResult = MutableStateFlow("")

    init {
        getFiats()
    }

    override fun handleEvent(event: ExchangeViewEvent) {
        when (event) {
            is OnAmountChange -> onAmountChange(event.amount)
            is OnFromChange -> onFromChange(event.from)
            is OnToChange -> onToChange(event.to)
            is OnConvertClick -> onConvertClick()
        }
    }

    private fun getFiats() {
        mainRepository.getCoinCapRates()
            .asResult()
            .map(::mapToState)
            .launchIn(viewModelScope)
    }

    private fun mapToState(it: NetworkResult<List<DataDao>>) = when (it) {
        is NetworkResult.Success -> {
            val fiats = it.data.filter { rates -> rates.type == FIAT }
            if (fiats.isNotEmpty()) {
                setState { Success(fiats) }
            } else {
                setState { Error("No fiat currencies found") }
            }
        }

        is NetworkResult.Error -> setState { Error(it.exception.message.toString()) }
        else -> setState { ExchangeState.Loading }
    }

    private fun onFromChange(from: String) {
        fromValue.value = from
    }

    private fun onToChange(to: String) {
        toValue.value = to
    }

    private fun onAmountChange(amount: String) {
        amountValue.value = amount
    }

    private fun onConvertClick() {
        // todo: implement conversion logic - validations


    }
}