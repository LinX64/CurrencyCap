package ui.screens.main.profile

import data.remote.model.User

sealed interface ProfileViewEvent {
    data class OnDeleteAccountCardClicked(val uid: String) : ProfileViewEvent
    data object OnSupportClicked : ProfileViewEvent
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
}