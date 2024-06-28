package ui.screens.profile

import data.remote.model.User
import domain.model.main.Data

sealed interface ProfileViewEvent {
    data class OnDeleteAccountCardClicked(val uid: String) : ProfileViewEvent
    data class OnHelpCenterCardClicked(val data: Data) : ProfileViewEvent
    data class OnProfileCardClicked(val data: Data) : ProfileViewEvent
}

sealed interface ProfileState {
    data object Idle : ProfileState
    data object Loading : ProfileState
    data class Success(val user: User) : ProfileState
    data class Error(val message: String) : ProfileState
}

sealed interface ProfileNavigationEffect {
    data object NavigateToLanding : ProfileNavigationEffect
}