package ui.screens.main.exchange

import androidx.compose.ui.graphics.Color
import data.local.model.exchange.Currency
import data.local.model.exchange.CurrencyCode
import data.local.model.exchange.CurrencyType

sealed interface ExchangeViewEvent {
    data object OnFetchRates : ExchangeViewEvent
    data object OnSwitchCurrencies : ExchangeViewEvent

    data class OnSaveSelectedCurrencyCode(
        val currencyType: CurrencyType,
        val currencyCode: CurrencyCode
    ) : ExchangeViewEvent

    data object OnReadSourceCurrencyCode : ExchangeViewEvent
    data object OnReadTargetCurrencyCode : ExchangeViewEvent
    data class OnAmountValueChanged(val value: String) : ExchangeViewEvent
}

sealed interface ExchangeState {

    data object Idle : ExchangeState

    data class ExchangeUiState(
        val rateState: RateStatus = RateStatus.Idle,
        val loading: Boolean = false,
        val sourceCurrency: Currency? = null,
        val targetCurrency: Currency? = null,
        val currencyRates: List<Currency> = emptyList(),
        val sourceCurrencyAmount: String = "0",
        val targetCurrencyAmount: String = "0"
    )
}

sealed interface ExchangeNavigationEffect {
    data class ShowSnakeBar(val message: String) : ExchangeNavigationEffect
}

enum class RateStatus(
    val title: String,
    val color: Color
) {
    Idle(
        title = "Rates",
        color = Color.White
    ),
    Fresh(
        title = "Fresh rates",
        color = Color(0xFF00FF00)
    ),
    Stale(
        title = "Rates are not fresh",
        color = Color(0xFFFF0000)
    )
}