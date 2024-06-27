package ui.screens.exchange

import data.model.exchange.CurrencyCode
import data.model.exchange.CurrencyType

sealed class HomeEvent {
    data object FetchRates : HomeEvent()
    data object SwitchCurrencies : HomeEvent()
    data class OnAmountChanged(val amount: String) : HomeEvent()

    data class SaveSelectedCurrencyCode(
        val currencyType: CurrencyType,
        val currencyCode: CurrencyCode
    ) : HomeEvent()

    data object ReadSourceCurrencyCode : HomeEvent()
    data object ReadTargetCurrencyCode : HomeEvent()
    data class NumberButtonClicked(val value: String) : HomeEvent()

}