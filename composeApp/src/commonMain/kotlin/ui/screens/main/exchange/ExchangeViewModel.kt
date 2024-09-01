package ui.screens.main.exchange

import androidx.lifecycle.viewModelScope
import data.local.model.exchange.CurrencyType.None
import data.local.model.exchange.CurrencyType.Source
import data.local.model.exchange.CurrencyType.Target
import data.util.Constant.ALL_RATES_KEY
import domain.model.CurrencyRate
import domain.model.main.Currencies
import domain.repository.CurrencyRepository
import domain.repository.MainRepository
import domain.usecase.ConvertCurrenciesUseCase
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import ui.common.MviViewModel
import ui.screens.main.exchange.ExchangeViewEvent.OnConvert
import ui.screens.main.exchange.ExchangeViewEvent.OnFetchRates
import ui.screens.main.exchange.ExchangeViewEvent.OnReadCurrencySourceCode
import ui.screens.main.exchange.ExchangeViewEvent.OnReadCurrencyTargetCode
import ui.screens.main.exchange.ExchangeViewEvent.OnSaveSelectedCurrencyCode
import ui.screens.main.exchange.ExchangeViewEvent.OnSwitchCurrencies

internal class ExchangeViewModel(
    private val currencyRepository: CurrencyRepository,
    private val mainRepository: MainRepository,
    private val convertCurrenciesUseCase: ConvertCurrenciesUseCase
) : MviViewModel<ExchangeViewEvent, ExchangeState, ExchangeNavigationEffect>(ExchangeUiState()) {

    private val amountFlow = MutableStateFlow("")

    init {
        handleEvent(OnFetchRates)
        handleEvent(OnReadCurrencySourceCode)
        handleEvent(OnReadCurrencyTargetCode)
        setupAmountObserver()
    }

    override fun handleEvent(event: ExchangeViewEvent) {
        when (event) {
            is OnFetchRates -> fetchRates()
            is OnReadCurrencySourceCode -> readCurrencySource()
            is OnReadCurrencyTargetCode -> readCurrencyTarget()
            is OnSaveSelectedCurrencyCode -> saveSelectedCurrencyCode(event)
            is OnSwitchCurrencies -> switchCurrencies()
            is OnConvert -> amountFlow.value = event.amount
        }
    }

    private fun fetchRates() {
        setupAmountObserver()

        viewModelScope.launch {
            mainRepository.getAllRatesNew()
                .stream(StoreReadRequest.cached(ALL_RATES_KEY, false))
                .collectLatest { response ->
                    when (response) {
                        is StoreReadResponse.Data -> {
                            val data = response.value
                            setState { ExchangeUiState(rates = mapIRRFiatToToman(data)) }
                        }

                        is StoreReadResponse.Error -> {
                            setState { ExchangeUiState(rates = persistentSetOf()) }
                        }

                        else -> Unit
                    }
                }
        }
    }

    private fun readCurrencySource() {
        viewModelScope.launch {
            currencyRepository.readSourceCurrencyCode().collectLatest { currencyCode ->
                viewState.collectLatest { currentState ->
                    if (currentState is ExchangeUiState) {
                        val selectedCurrency =
                            currentState.rates.find { it.code == currencyCode.name }

                        selectedCurrency?.let { nonNullData ->
                            setState { if (this is ExchangeUiState) copy(sourceCurrencyRate = nonNullData) else this }
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
                        val selectedCurrency =
                            currentState.rates.find { it.code == currencyCode.name }

                        selectedCurrency?.let { nonNullData ->
                            setState { if (this is ExchangeUiState) copy(targetCurrencyRate = nonNullData) else this }
                        }
                    }
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun setupAmountObserver() {
        amountFlow
            .debounce(300)
            .distinctUntilChanged()
            .onEach(::convertCurrency)
            .launchIn(viewModelScope)
    }

    private fun saveSelectedCurrencyCode(event: OnSaveSelectedCurrencyCode) {
        when (event.currencyType) {
            is Source -> saveCurrencySourceCode(event.currencyCode.name)
            is Target -> saveCurrencyTargetCode(event.currencyCode.name)
            None -> Unit
        }
    }

    private fun switchCurrencies() {
        viewModelScope.launch {
            val currentState = viewState.value
            if (currentState is ExchangeUiState) {
                val source = currentState.sourceCurrencyRate ?: return@launch
                val target = currentState.targetCurrencyRate ?: return@launch

                saveCurrencyTargetCode(source.code)
                saveCurrencySourceCode(target.code)
            }
        }

        // Update the UI
        convertCurrency(amountFlow.value)
    }

    private fun convertCurrency(amount: String) {
        val amountDouble = amount.toDoubleOrNull()
        val currentState = viewState.value

        if (currentState is ExchangeUiState) {
            val source = currentState.sourceCurrencyRate
            val target = currentState.targetCurrencyRate

            val convertedValue = convertCurrenciesUseCase(amountDouble, source, target)
            setState { if (this is ExchangeUiState) copy(convertedAmount = convertedValue) else this }
        }
    }

    private fun saveCurrencySourceCode(code: String) {
        viewModelScope.launch { currencyRepository.saveSourceCurrencyCode(code) }
    }

    private fun saveCurrencyTargetCode(code: String) {
        viewModelScope.launch { currencyRepository.saveTargetCurrencyCode(code) }
    }

    private fun mapIRRFiatToToman(data: Currencies): ImmutableSet<CurrencyRate> {
        val iranianRealRate = data.bonbast.first { it.code == "USD" }.sell

        return data.rates
            .filter { it.type == FIAT && it.symbol !in unrecognizedSymbols }
            .map { rate ->
                CurrencyRate(
                    code = rate.symbol,
                    value = if (rate.symbol == "IRR") iranianRealRate else rate.rateUsd.toDouble(),
                )
            }.toImmutableSet()
    }

    companion object {
        private const val FIAT = "fiat"
        private val unrecognizedSymbols = setOf(
            "CNH", "XAG", "PEN", "UYU", "AWG", "KYD", "XOF", "XPT",
            "XPF", "XAU", "XDR", "BND", "UGX", "XCD", "BIF", "XPD",
            "SSP", "SZL", "SHP"
        )
    }
}