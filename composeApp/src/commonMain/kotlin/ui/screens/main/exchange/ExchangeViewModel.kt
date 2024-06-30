package ui.screens.main.exchange

import androidx.lifecycle.viewModelScope
import data.local.model.exchange.CurrencyType
import data.util.NetworkResult
import data.util.asResult
import domain.repository.CurrencyRepository
import domain.repository.ExchangeRepository
import domain.usecase.ConvertCurrenciesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.main.exchange.ExchangeViewEvent.OnConvertClicked
import ui.screens.main.exchange.ExchangeViewEvent.OnFetchRates
import ui.screens.main.exchange.ExchangeViewEvent.OnReadSourceCurrencyCode
import ui.screens.main.exchange.ExchangeViewEvent.OnReadTargetCurrencyCode
import ui.screens.main.exchange.ExchangeViewEvent.OnSaveSelectedCurrencyCode
import ui.screens.main.exchange.ExchangeViewEvent.OnSwitchCurrencies

internal class ExchangeViewModel(
    private val currencyRepository: CurrencyRepository,
    private val exchangeRepository: ExchangeRepository,
    private val convertCurrenciesUseCase: ConvertCurrenciesUseCase
) : MviViewModel<ExchangeViewEvent, ExchangeState, ExchangeNavigationEffect>(ExchangeState.Idle) {

    private val _state = MutableStateFlow(ExchangeState.ExchangeUiState())
    val state = _state.asStateFlow()

    init {
        handleEvent(OnFetchRates)
        handleEvent(OnReadSourceCurrencyCode)
        handleEvent(OnReadTargetCurrencyCode)
    }

    override fun handleEvent(event: ExchangeViewEvent) {
        when (event) {
            is OnFetchRates -> fetchRates()
            is OnReadSourceCurrencyCode -> readSourceCurrency()
            is OnReadTargetCurrencyCode -> readTargetCurrency()
            is OnSaveSelectedCurrencyCode -> saveSelectedCurrencyCode(event)
            is OnSwitchCurrencies -> switchCurrencies()
            is OnConvertClicked -> convertCurrency(event.amount)
        }
    }

    private fun convertCurrency(amount: String) {
        val amountDouble = amount.toDoubleOrNull()
        val sourceCurrency = state.value.sourceCurrency
        val targetCurrency = state.value.targetCurrency

        val convertedValue = convertCurrenciesUseCase(amountDouble, sourceCurrency, targetCurrency) ?: 0.0
        _state.update { it.copy(convertedAmount = convertedValue) }
    }

    private fun saveSelectedCurrencyCode(event: OnSaveSelectedCurrencyCode) {
        if (event.currencyType is CurrencyType.Source) {
            saveSourceCurrencyCode(event.currencyCode.name)
        } else if (event.currencyType is CurrencyType.Target) {
            saveTargetCurrencyCode(event.currencyCode.name)
        }
    }

    private fun fetchRates() {
        exchangeRepository.getLatest()
            .asResult()
            .map { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val currencyRates = result.data.sortedBy { it.code }
                        _state.update { it.copy(currencyRates = currencyRates) }
                    }

                    is NetworkResult.Error -> _state.update { it.copy(currencyRates = emptyList()) }
                    else -> Unit
                }
            }
            .launchIn(viewModelScope)
    }

    private fun readTargetCurrency() {
        viewModelScope.launch {
            currencyRepository.readTargetCurrencyCode().collectLatest { currencyCode ->
                state.collect { uiState ->
                    val selectedCurrency = uiState.currencyRates.find { it.code == currencyCode.name }

                    selectedCurrency?.let { nonNullData ->
                        _state.update {
                            it.copy(targetCurrency = nonNullData)
                        }
                    }
                }
            }
        }
    }

    private fun switchCurrencies() {
        viewModelScope.launch {
            val source = state.value.sourceCurrency ?: return@launch
            val target = state.value.targetCurrency ?: return@launch

            saveTargetCurrencyCode(source.code)
            saveSourceCurrencyCode(target.code)
        }
    }

    private fun saveSourceCurrencyCode(code: String) {
        viewModelScope.launch {
            currencyRepository.saveSourceCurrencyCode(code)
        }
    }

    private fun readSourceCurrency() {
        viewModelScope.launch {
            currencyRepository.readSourceCurrencyCode().collectLatest { currencyCode ->
                state.collect { uiState ->
                    val selectedCurrency = uiState.currencyRates.find { it.code == currencyCode.name }

                    selectedCurrency?.let { nonNullData ->
                        _state.update {
                            it.copy(sourceCurrency = nonNullData)
                        }
                    }
                }

            }
        }
    }

    private fun saveTargetCurrencyCode(code: String) {
        viewModelScope.launch {
            currencyRepository.saveTargetCurrencyCode(code)
        }
    }
}