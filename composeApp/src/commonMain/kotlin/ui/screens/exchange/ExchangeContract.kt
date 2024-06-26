package ui.screens.exchange

import androidx.compose.ui.graphics.Color
import domain.model.RateDto

sealed interface ExchangeViewEvent {
    data class OnAmountChange(val amount: String) : ExchangeViewEvent
    data object OnSwapClick : ExchangeViewEvent
    data object OnConvertClick : ExchangeViewEvent
    data object OnSwitchCurrency : ExchangeViewEvent

    data object ReadSourceCurrencyCode : ExchangeViewEvent
    data object ReadTargetCurrencyCode : ExchangeViewEvent
}

sealed interface ExchangeState {
    data object Loading : ExchangeState
    data class Success(val rates: List<RateDto>) : ExchangeState
    data class Error(val message: String) : ExchangeState
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

data class HomeUiState(
    val rateState: RateStatus = RateStatus.Idle,
    val loading: Boolean = false,
    val sourceCurrency: Currency? = null,
    val targetCurrency: Currency? = null,
    val currencyRates: List<Currency> = emptyList(),
    val sourceCurrencyAmount: String = "0",
    val targetCurrencyAmount: String = "0"
)