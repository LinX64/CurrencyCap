package ui.screens.exchange

import androidx.lifecycle.viewModelScope
import data.util.NetworkResult
import data.util.asResult
import domain.model.CurrenciesDto
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
import ui.screens.exchange.ExchangeViewEvent.OnSwapClick
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
        mainRepository.getAllRates()
            .asResult()
            .map { mapToState(it) }
            .distinctUntilChanged()
            .flatMapLatest { flowOf(it) }
            .launchIn(viewModelScope)
    }

    override fun handleEvent(event: ExchangeViewEvent) {
        when (event) {
            is OnAmountChange -> amountValue.value = event.amount
            is OnFromChange -> fromValue.value = event.from
            is OnToChange -> toValue.value = event.to
            is OnConvertClick -> onConvertClick()
            OnSwapClick -> onSwapClicked()
        }
    }

    private fun mapToState(it: NetworkResult<CurrenciesDto>) = when (it) {
        is NetworkResult.Success -> {
            val fiats = it.data.rates.filter { it.type == FIAT }
            if (fiats.isNotEmpty()) {
                setState { Success(fiats) }
            } else setState { Error("No fiat currencies found") }
        }

        is NetworkResult.Error -> setState { Error(it.exception.message.toString()) }
        else -> setState { ExchangeState.Loading }
    }

    private fun onConvertClick() {
        if (fromValue.value.isEmpty() || toValue.value.isEmpty() || amountValue.value.isEmpty()) {
            setEffect(ExchangeNavigationEffect.ShowSnakeBar("Please fill all fields!"))
            return
        }

        if (fromValue.value == toValue.value) {
            setEffect(ExchangeNavigationEffect.ShowSnakeBar("Cannot convert the same currency, please select different currency!"))
            return
        }

        viewModelScope.launch {
            val result = convertRateUseCase(
                from = fromValue.value,
                to = toValue.value,
                amount = amountValue.value
            )

            convertedResult.value = result
        }
    }

    private fun onSwapClicked() {
        val from = fromValue.value
        val to = toValue.value
        fromValue.value = to
        toValue.value = from
    }
}