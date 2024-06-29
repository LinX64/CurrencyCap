package ui.screens.exchange

import androidx.lifecycle.viewModelScope
import data.local.model.exchange.CurrencyType
import data.util.NetworkResult
import data.util.asResult
import domain.repository.CurrencyRepository
import domain.repository.ExchangeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.exchange.ExchangeViewEvent.OnAmountValueChanged
import ui.screens.exchange.ExchangeViewEvent.OnFetchRates
import ui.screens.exchange.ExchangeViewEvent.OnReadSourceCurrencyCode
import ui.screens.exchange.ExchangeViewEvent.OnReadTargetCurrencyCode
import ui.screens.exchange.ExchangeViewEvent.OnSaveSelectedCurrencyCode
import ui.screens.exchange.ExchangeViewEvent.OnSwitchCurrencies

internal class ExchangeViewModel(
    private val currencyRepository: CurrencyRepository,
    private val exchangeRepository: ExchangeRepository
) : MviViewModel<ExchangeViewEvent, ExchangeState, ExchangeNavigationEffect>(ExchangeState.Idle) {

    private val _state = MutableStateFlow(ExchangeState.ExchangeUiState())
    val state = _state.asStateFlow()
    private val _updatedCurrencyValue: MutableStateFlow<String> = MutableStateFlow("")

    init {
        handleEvent(OnFetchRates)
        handleEvent(OnReadSourceCurrencyCode)
        handleEvent(OnReadTargetCurrencyCode)

        updateCurrencySource()
        convertCurrency()
    }

    override fun handleEvent(event: ExchangeViewEvent) {
        when (event) {
            is OnFetchRates -> fetchRates()
            is OnReadSourceCurrencyCode -> readSourceCurrency()
            is OnReadTargetCurrencyCode -> readTargetCurrency()
            is OnSaveSelectedCurrencyCode -> saveSelectedCurrencyCode(event)
            is OnSwitchCurrencies -> switchCurrencies()
            is OnAmountValueChanged -> updateCurrencyValue(event.value)
        }
    }

    private fun updateCurrencySource() {
        viewModelScope.launch {
            _updatedCurrencyValue.collect { value ->
                _state.update {
                    it.copy(sourceCurrencyAmount = value)
                }
            }
        }
    }

    private fun convertCurrency() {
        viewModelScope.launch {
            state.collectLatest {
                _state.update {
                    it.copy(targetCurrencyAmount = convert(it.sourceCurrencyAmount).toString())
                }
            }
        }
    }

    private fun convert(amount: String): Double {
        val source = state.value.sourceCurrency?.value
        val target = state.value.targetCurrency?.value

        try {
            val exchangeRate = if (source != null && target != null) {
                target / source
            } else 1.0

            return if (amount.isEmpty()) 0.0
            else amount.toDouble() * exchangeRate
        } catch (e: NumberFormatException) {
            return 0.0
        }
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

    private fun updateCurrencyValue(value: String) {
        val currentCurrencyValue = state.value.sourceCurrencyAmount
        _updatedCurrencyValue.update {
            when (value) {
                "C" -> "0"
                else ->
                    if (currentCurrencyValue == "0") value else {
                        (currentCurrencyValue + value).run {
                            if (this.length > 9) {
                                this.substring(0, 9)
                            } else {
                                this
                            }
                        }
                    }
            }
        }

        // TODO: Check this and fix it
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