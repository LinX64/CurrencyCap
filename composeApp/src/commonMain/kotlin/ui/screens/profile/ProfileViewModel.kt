package ui.screens.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import data.repository.datastore.user.UserPreferences
import domain.repository.AuthService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.profile.ProfileViewEvent.OnDeleteAccountCardClicked
import ui.screens.profile.ProfileViewEvent.OnHelpCenterCardClicked
import ui.screens.profile.ProfileViewEvent.OnProfileCardClicked

internal class ProfileViewModel(
    private val authService: AuthService,
    private val userPreferences: UserPreferences
) : MviViewModel<ProfileViewEvent, ProfileState, ProfileNavigationEffect>(ProfileState.Idle) {

    val uid: MutableState<String> = mutableStateOf("")

    init {
        fetchProfile()
    }

    override fun handleEvent(event: ProfileViewEvent) {
        when (event) {
            is OnProfileCardClicked -> TODO()
            is OnHelpCenterCardClicked -> TODO()
            is OnDeleteAccountCardClicked -> handleDeleteAccount()
        }
    }

    private fun handleDeleteAccount() {
        setState { ProfileState.Loading }

        viewModelScope.launch {
            delay(2000)

            userPreferences.clear()
            authService.deleteAccount()

            setState { ProfileState.Idle }
            setEffect(ProfileNavigationEffect.NavigateToLanding)
        }
    }

    private fun fetchProfile() {
        val user = authService.currentUser

        viewModelScope.launch {
            user.collect { user ->
                uid.value = user.id
                setState { ProfileState.Success(user) }
            }
        }
    }
}