package ui.screens.initial.get_verified

sealed interface GetVerifiedPhoneViewEvent {
    data class OnCodeChanged(val code: String) : GetVerifiedPhoneViewEvent
    data object OnFinishSignUpClick : GetVerifiedPhoneViewEvent
}

sealed interface GetVerifiedPhoneState {
    data object Loading : GetVerifiedPhoneState
    data class Success(val uid: String) : GetVerifiedPhoneState
    data class Error(val message: String) : GetVerifiedPhoneState
}

sealed interface GetVerifiedPhoneNavigationEffect {
    data object NavigateToMarketOverview : GetVerifiedPhoneNavigationEffect
}

