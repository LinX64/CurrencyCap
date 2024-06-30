package ui.screens.main.exchange

import androidx.compose.ui.graphics.Color
import data.local.model.exchange.Currency
import data.local.model.exchange.CurrencyCode
import data.local.model.exchange.CurrencyType

sealed interface ExchangeViewEvent {
    data object OnFetchRates : ExchangeViewEvent
    data object OnSwitchCurrencies : ExchangeViewEvent
    data class OnConvertClicked(val amount: String) : ExchangeViewEvent

    data class OnSaveSelectedCurrencyCode(
        val currencyType: CurrencyType,
        val currencyCode: CurrencyCode
    ) : ExchangeViewEvent

    data object OnReadSourceCurrencyCode : ExchangeViewEvent
    data object OnReadTargetCurrencyCode : ExchangeViewEvent
}

sealed interface ExchangeState {
    data object Idle : ExchangeState

    data class ExchangeUiState(
        val isLoading: Boolean = false,
        val rateState: RateStatus = RateStatus.Idle,
        val sourceCurrency: Currency? = null,
        val targetCurrency: Currency? = null,
        val convertedAmount: Double = 0.0,
        val currencyRates: List<Currency> = emptyList(),
        val sourceCurrencyAmount: CurrencyCode = CurrencyCode.USD,
        val targetCurrencyAmount: CurrencyCode = CurrencyCode.EUR
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