package ui.screens.exchange

import domain.model.RateDto

sealed interface ExchangeViewEvent {
    data class OnFromChange(val from: String) : ExchangeViewEvent
    data class OnToChange(val to: String) : ExchangeViewEvent
    data class OnAmountChange(val amount: String) : ExchangeViewEvent
    data object OnConvertClick : ExchangeViewEvent
}

sealed interface ExchangeState {
    data object Loading : ExchangeState
    data class Success(val rates: List<RateDto>) : ExchangeState
    data class Error(val message: String) : ExchangeState
}

sealed interface ExchangeNavigationEffect {
    data class ShowSnakeBar(val message: String) : ExchangeNavigationEffect
}

