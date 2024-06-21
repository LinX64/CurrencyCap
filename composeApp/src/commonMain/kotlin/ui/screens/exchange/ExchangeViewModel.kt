package ui.screens.exchange

import androidx.lifecycle.viewModelScope
import data.util.NetworkResult
import data.util.asResult
import domain.model.DataDao
import domain.repository.MainRepository
import domain.usecase.ConvertRateUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.exchange.ExchangeState.Error
import ui.screens.exchange.ExchangeState.Success
import ui.screens.exchange.ExchangeViewEvent.OnAmountChange
import ui.screens.exchange.ExchangeViewEvent.OnConvertClick
import ui.screens.exchange.ExchangeViewEvent.OnFromChange
import ui.screens.exchange.ExchangeViewEvent.OnToChange

private const val FIAT = "fiat"

@OptIn(ExperimentalCoroutinesApi::class)
internal class ExchangeViewModel(
    private val mainRepository: MainRepository,
    private val convertRateUseCase: ConvertRateUseCase
) : MviViewModel<ExchangeViewEvent, ExchangeState, ExchangeNavigationEffect>(ExchangeState.Loading) {

    val fromValue: MutableStateFlow<String> = MutableStateFlow("")
    val toValue: MutableStateFlow<String> = MutableStateFlow("")
    val amountValue: MutableStateFlow<String> = MutableStateFlow("")
    val convertedResult: MutableStateFlow<String> = MutableStateFlow("")

    init {
        getFiats()
    }

    private fun getFiats() {
        mainRepository.getCoinCapRates()
            .asResult()
            .map { mapToState(it) }
            .distinctUntilChanged()
            .flatMapLatest { flowOf(it) }
            .launchIn(viewModelScope)
    }

    override fun handleEvent(event: ExchangeViewEvent) {
        when (event) {
            is OnAmountChange -> onAmountChange(event.amount)
            is OnFromChange -> onFromChange(event.from)
            is OnToChange -> onToChange(event.to)
            is OnConvertClick -> onConvertClick()
        }
    }

    private fun mapToState(it: NetworkResult<List<DataDao>>) = when (it) {
        is NetworkResult.Success -> {
            val fiats = it.data.filter { rates -> rates.type == FIAT }
            if (fiats.isNotEmpty()) {
                setState { Success(fiats) }
            } else setState { Error("No fiat currencies found") }
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
        if (fromValue.value.isEmpty() || toValue.value.isEmpty() || amountValue.value.isEmpty()) {
            return
        }

        if (fromValue.value == toValue.value) {
            setEffect(ExchangeNavigationEffect.ShowSnakeBar("Cannot convert the same currency, please select different currency!"))
            return
        }

        viewModelScope.launch {
            convertRateUseCase(fromValue.value, toValue.value, amountValue.value)
                .also { convertedResult.value = it }
        }
    }
}