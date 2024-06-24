package ui.screens.auth.fill_profile

sealed interface FillProfileViewEvent {
    data class EmailChanged(val email: String) : FillProfileViewEvent
    data class PasswordChanged(val password: String) : FillProfileViewEvent
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
}

