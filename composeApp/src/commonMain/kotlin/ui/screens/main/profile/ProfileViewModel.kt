package ui.screens.main.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import domain.repository.AuthServiceRepository
import domain.repository.UserPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.main.profile.ProfileViewEvent.OnDeleteAccountCardClicked
import ui.screens.main.profile.ProfileViewEvent.OnSupportClicked

internal class ProfileViewModel(
    private val authServiceRepository: AuthServiceRepository,
    private val userPreferences: UserPreferences
) : MviViewModel<ProfileViewEvent, ProfileState, ProfileNavigationEffect>(ProfileState.Idle) {

    val uid: MutableState<String> = mutableStateOf("")

    init {
        fetchProfile()
    }

    override fun handleEvent(event: ProfileViewEvent) {
        when (event) {
            OnSupportClicked -> setEffect(ProfileNavigationEffect.OpenEmailApp)
            is OnDeleteAccountCardClicked -> handleDeleteAccount()
        }
    }

    private fun handleDeleteAccount() {
        setState { ProfileState.Loading }

        viewModelScope.launch {
            delay(2000)

            userPreferences.clear()
            authServiceRepository.deleteAccount()

            setState { ProfileState.Idle }
            setEffect(ProfileNavigationEffect.NavigateToLanding)
        }
    }

    private fun fetchProfile() {
        val user = authServiceRepository.currentUser

        viewModelScope.launch {
            user.collect { user ->
                uid.value = user.id
                setState { ProfileState.Success(user) }
            }
        }
    }
}