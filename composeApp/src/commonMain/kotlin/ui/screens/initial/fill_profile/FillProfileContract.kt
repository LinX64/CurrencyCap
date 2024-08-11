package ui.screens.initial.fill_profile

sealed interface FillProfileViewEvent {
    data class OnNameChanged(val fullName: String) : FillProfileViewEvent
    data class OnPhoneNumberChanged(val phoneNumber: String) : FillProfileViewEvent
    data class OnPasswordError(val message: String) : FillProfileViewEvent
    data object OnSignUpClick : FillProfileViewEvent
    data object OnSkipClicked : FillProfileViewEvent
}

sealed interface FillProfileState {
    data object Idle : FillProfileState
    data object Loading : FillProfileState
    data class Success(val uid: String) : FillProfileState
    data class Error(val message: String) : FillProfileState
}

sealed interface FillProfileNavigationEffect {
    data object NavigateToMarketOverview : FillProfileNavigationEffect
    data class ShowError(val message: String) : FillProfileNavigationEffect
}

