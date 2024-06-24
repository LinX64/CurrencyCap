package ui.screens.profile

import data.model.User
import domain.model.DataDao

sealed interface ProfileViewEvent {
    data class OnDeleteAccountCardClicked(val uid: String) : ProfileViewEvent
    data class OnHelpCenterCardClicked(val data: DataDao) : ProfileViewEvent
    data class OnProfileCardClicked(val data: DataDao) : ProfileViewEvent
}

sealed interface ProfileState {
    data object Idle : ProfileState
    data object Loading : ProfileState
    data class Success(val user: User) : ProfileState
    data class Error(val message: String) : ProfileState
}

sealed interface ProfileNavigationEffect {
    data class ShowSnakeBar(val message: String) : ProfileNavigationEffect
}

