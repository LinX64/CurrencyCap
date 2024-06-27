package ui.screens.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.exchange.CurrencyType
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

internal class ExchangeViewModel(
    private val currencyRepository: CurrencyRepository,
    private val exchangeRepository: ExchangeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()
    private val _updatedCurrencyValue: MutableStateFlow<String> = MutableStateFlow("")

    init {
        onEvent(HomeEvent.FetchRates)
        onEvent(HomeEvent.ReadSourceCurrencyCode)
        onEvent(HomeEvent.ReadTargetCurrencyCode)

        viewModelScope.launch {
            _updatedCurrencyValue.collect { value ->
                _state.update {
                    it.copy(sourceCurrencyAmount = value)
                }
            }
        }
        viewModelScope.launch {
            state.collect {
                _state.update {
                    it.copy(
                        targetCurrencyAmount = convert(it.sourceCurrencyAmount).toString()
                    )
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

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.FetchRates -> fetchRates()
            is HomeEvent.ReadSourceCurrencyCode -> readSourceCurrency()
            is HomeEvent.ReadTargetCurrencyCode -> readTargetCurrency()
            is HomeEvent.SaveSelectedCurrencyCode -> {
                if (event.currencyType is CurrencyType.Source) {
                    saveSourceCurrencyCode(event.currencyCode.name)
                } else if (event.currencyType is CurrencyType.Target) {
                    saveTargetCurrencyCode(event.currencyCode.name)
                }
            }

            is HomeEvent.SwitchCurrencies -> switchCurrencies()
            is HomeEvent.NumberButtonClicked -> updateCurrencyValue(event.value)
            is HomeEvent.OnAmountChanged -> {
                _state.update {
                    it.copy(sourceCurrencyAmount = event.amount)
                }
            }
        }
    }

    private fun fetchRates() {
        exchangeRepository.getLatest()
            .asResult()
            .map { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val currencyRates = result.data.sortedBy { it.code }
                        _state.update {
                            it.copy(currencyRates = currencyRates)
                        }
                    }

                    is NetworkResult.Error -> {
                        _state.update {
                            it.copy(currencyRates = emptyList())
                        }
                    }

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
    }

    private fun saveSourceCurrencyCode(code: String) {
        viewModelScope.launch {
            currencyRepository.saveSourceCurrencyCode(code)
        }
    }

    private fun readSourceCurrency() {
        viewModelScope.launch {
            // todo
        }
    }

    private fun saveTargetCurrencyCode(code: String) {
        viewModelScope.launch {
            currencyRepository.saveTargetCurrencyCode(code)
        }
    }
}