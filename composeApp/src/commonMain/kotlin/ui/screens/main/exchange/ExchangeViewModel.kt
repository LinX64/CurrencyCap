package ui.screens.main.exchange

import androidx.lifecycle.viewModelScope
import data.local.model.exchange.CurrencyType
import data.util.NetworkResult
import data.util.asResult
import domain.repository.CurrencyRepository
import domain.repository.ExchangeRepository
import domain.usecase.ConvertCurrenciesUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.main.exchange.ExchangeViewEvent.OnConvert
import ui.screens.main.exchange.ExchangeViewEvent.OnFetchRates
import ui.screens.main.exchange.ExchangeViewEvent.OnReadCurrencySourceCode
import ui.screens.main.exchange.ExchangeViewEvent.OnReadCurrencyTargetCode
import ui.screens.main.exchange.ExchangeViewEvent.OnSaveSelectedCurrencyCode
import ui.screens.main.exchange.ExchangeViewEvent.OnSwitchCurrencies

internal class ExchangeViewModel(
    private val currencyRepository: CurrencyRepository,
    private val exchangeRepository: ExchangeRepository,
    private val convertCurrenciesUseCase: ConvertCurrenciesUseCase
) : MviViewModel<ExchangeViewEvent, ExchangeState, ExchangeNavigationEffect>(ExchangeUiState()) {

    init {
        handleEvent(OnFetchRates)
        handleEvent(OnReadCurrencySourceCode)
        handleEvent(OnReadCurrencyTargetCode)
    }

    override fun handleEvent(event: ExchangeViewEvent) {
        when (event) {
            is OnFetchRates -> fetchRates()
            is OnReadCurrencySourceCode -> readCurrencySource()
            is OnReadCurrencyTargetCode -> readCurrencyTarget()
            is OnSaveSelectedCurrencyCode -> saveSelectedCurrencyCode(event)
            is OnSwitchCurrencies -> switchCurrencies()
            is OnConvert -> convertCurrency(event.amount)
        }
    }

    private fun fetchRates() {
        exchangeRepository.getLatest()
            .asResult()
            .map { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val currencyRates = result.data.sortedBy { it.code }
                        setState { ExchangeUiState(currencyRates = currencyRates) }
                    }

                    is NetworkResult.Error -> setState { ExchangeUiState(currencyRates = emptyList()) }
                    else -> Unit
                }
            }
            .launchIn(viewModelScope)
    }

    private fun readCurrencySource() {
        viewModelScope.launch {
            currencyRepository.readSourceCurrencyCode().collectLatest { currencyCode ->
                viewState.collectLatest { currentState ->
                    if (currentState is ExchangeUiState) {
                        val selectedCurrency = currentState.currencyRates.find { it.code == currencyCode.name }

                        selectedCurrency?.let { nonNullData ->
                            setState { if (this is ExchangeUiState) copy(sourceCurrency = nonNullData) else this }
                        }
                    }
                }
            }
        }
    }

    private fun readCurrencyTarget() {
        viewModelScope.launch {
            currencyRepository.readTargetCurrencyCode().collectLatest { currencyCode ->
                viewState.collectLatest { currentState ->
                    if (currentState is ExchangeUiState) {
                        val selectedCurrency = currentState.currencyRates.find { it.code == currencyCode.name }

                        selectedCurrency?.let { nonNullData ->
                            setState { if (this is ExchangeUiState) copy(targetCurrency = nonNullData) else this }
                        }
                    }
                }
            }
        }
    }

    private fun saveSelectedCurrencyCode(event: OnSaveSelectedCurrencyCode) {
        when (event.currencyType) {
            is CurrencyType.Source -> saveCurrencySourceCode(event.currencyCode.name)
            is CurrencyType.Target -> saveCurrencyTargetCode(event.currencyCode.name)
            CurrencyType.None -> Unit
        }
    }

    private fun switchCurrencies() {
        viewModelScope.launch {
            val currentState = viewState.value
            if (currentState is ExchangeUiState) {
                val source = currentState.sourceCurrency ?: return@launch
                val target = currentState.targetCurrency ?: return@launch

                saveCurrencyTargetCode(source.code)
                saveCurrencySourceCode(target.code)
            }
        }
    }

    private fun convertCurrency(amount: String) {
        val amountDouble = amount.toDoubleOrNull()
        val currentState = viewState.value

        if (currentState is ExchangeUiState) {
            val source = currentState.sourceCurrency
            val target = currentState.targetCurrency

            val convertedValue = convertCurrenciesUseCase(amountDouble, source, target)
            setState { if (this is ExchangeUiState) copy(convertedAmount = convertedValue) else this }
        }
    }

    private fun saveCurrencySourceCode(code: String) {
        viewModelScope.launch {
            currencyRepository.saveSourceCurrencyCode(code)
        }
    }

    private fun saveCurrencyTargetCode(code: String) {
        viewModelScope.launch {
            currencyRepository.saveTargetCurrencyCode(code)
        }
    }
}