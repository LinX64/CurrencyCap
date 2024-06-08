package ui.screens.exchange

sealed interface ExchangeViewEvent {
    data class AmountChange(val amount: String) : ExchangeViewEvent
    data class FromChange(val from: String) : ExchangeViewEvent
    data class ToChange(val to: String) : ExchangeViewEvent
    data object ConvertClick : ExchangeViewEvent
}

sealed interface ExchangeState {
    data object Loading : ExchangeState
    data class Success(val rates: List<ExchangeRate>) : ExchangeState
}

sealed interface ExchangeNavigationEffect {
    data class ShowToast(val message: String) : ExchangeNavigationEffect
}

data class ExchangeRate(
    val symbol: String,
    val rate: Double
)