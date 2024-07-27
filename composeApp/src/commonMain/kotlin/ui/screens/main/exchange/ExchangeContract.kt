package ui.screens.main.exchange

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import data.local.model.exchange.CurrencyCode
import data.local.model.exchange.CurrencyType
import domain.model.Currency
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf

sealed interface ExchangeViewEvent {
    data object OnFetchRates : ExchangeViewEvent
    data object OnSwitchCurrencies : ExchangeViewEvent
    data class OnConvert(val amount: String) : ExchangeViewEvent

    data class OnSaveSelectedCurrencyCode(
        val currencyType: CurrencyType,
        val currencyCode: CurrencyCode
    ) : ExchangeViewEvent

    data object OnReadCurrencySourceCode : ExchangeViewEvent
    data object OnReadCurrencyTargetCode : ExchangeViewEvent
}

sealed interface ExchangeState {
    data object Idle : ExchangeState
}

sealed interface ExchangeNavigationEffect {
    data class ShowSnakeBar(val message: String) : ExchangeNavigationEffect
}

@Stable
data class ExchangeUiState(
    val rateState: RateStatus = RateStatus.Idle,
    val sourceCurrency: Currency? = null,
    val targetCurrency: Currency? = null,
    val convertedAmount: Double = 0.0,
    val currencyRates: ImmutableSet<Currency> = persistentSetOf(),
    val sourceCurrencyAmount: CurrencyCode = CurrencyCode.USD,
    val targetCurrencyAmount: CurrencyCode = CurrencyCode.EUR
) : ExchangeState

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