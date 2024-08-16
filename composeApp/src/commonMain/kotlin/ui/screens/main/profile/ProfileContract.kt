package ui.screens.main.profile

import data.remote.model.User

sealed interface ProfileViewEvent {
    data object OnDeleteAccountCardClicked : ProfileViewEvent
    data object OnSupportClicked : ProfileViewEvent
    data object OnSignOutClicked : ProfileViewEvent
    data object OnFetchProfile : ProfileViewEvent
}

sealed interface ProfileState {
    data object Idle : ProfileState
    data object Loading : ProfileState
    data class Success(val user: User) : ProfileState
    data class Error(val message: String) : ProfileState
}

sealed interface ProfileNavigationEffect {
    data object NavigateToLanding : ProfileNavigationEffect
    data object OpenEmailApp : ProfileNavigationEffect
    data class ShowError(val message: String) : ProfileNavigationEffect
}