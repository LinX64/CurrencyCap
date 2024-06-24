package ui.screens.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import domain.repository.AuthService
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.profile.ProfileViewEvent.OnDeleteAccountCardClicked
import ui.screens.profile.ProfileViewEvent.OnHelpCenterCardClicked
import ui.screens.profile.ProfileViewEvent.OnProfileCardClicked

class ProfileViewModel(
    private val authService: AuthService
) : MviViewModel<ProfileViewEvent, ProfileState, ProfileNavigationEffect>(ProfileState.Idle) {

    val uid: MutableState<String> = mutableStateOf("")

    init {
        fetchProfile()
    }

    override fun handleEvent(event: ProfileViewEvent) {
        when (event) {
            is OnProfileCardClicked -> {

            }

            is OnHelpCenterCardClicked -> {

            }

            is OnDeleteAccountCardClicked -> handleDeleteAccount()
        }
    }

    private fun handleDeleteAccount() {
        setState { ProfileState.Loading }

        viewModelScope.launch {
            authService.deleteAccount()
        }
    }

    private fun fetchProfile() {
        val user = authService.currentUser
        viewModelScope.launch {
            user.collect { user ->
                setState { ProfileState.Success(user) }
            }
        }
    }
}