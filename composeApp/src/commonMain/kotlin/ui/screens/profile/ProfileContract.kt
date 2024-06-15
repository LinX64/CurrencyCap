package ui.screens.profile

import data.model.User
import domain.model.DataDao
import kotlinx.coroutines.flow.Flow

sealed interface ProfileViewEvent {
    data class OnDeleteAccountCardClicked(val uid: String) : ProfileViewEvent
    data class OnHelpCenterCardClicked(val data: DataDao) : ProfileViewEvent
    data class OnProfileCardClicked(val data: DataDao) : ProfileViewEvent
}

sealed interface ProfileState {
    data object Idle : ProfileState
    data class Loaded(val data: Flow<User>) : ProfileState
    data class Success(val rates: List<DataDao>) : ProfileState
    data class Error(val message: String) : ProfileState
}

sealed interface ProfileNavigationEffect {
    data class ShowSnakeBar(val message: String) : ProfileNavigationEffect
}

