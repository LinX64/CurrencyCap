package ui.screens.main.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import data.remote.model.User
import domain.repository.AuthServiceRepository
import domain.repository.ProfileRepository
import domain.repository.UserPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.main.profile.ProfileViewEvent.OnDeleteAccountCardClicked
import ui.screens.main.profile.ProfileViewEvent.OnSupportClicked

internal class ProfileViewModel(
    private val authServiceRepository: AuthServiceRepository,
    private val userPreferences: UserPreferences,
    private val profileRepository: ProfileRepository
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
        setState { ProfileState.Loading }
        val user = authServiceRepository.currentUser

        viewModelScope.launch {
            val fullName = profileRepository.getUserFullName()
            val phoneNumber = profileRepository.getUserPhoneNumber()
            val email = user.mapNotNull { it.email }.first()

            setState {
                ProfileState.Success(
                    User(
                        fullName = fullName,
                        phoneNumber = phoneNumber,
                        email = email
                    )
                )
            }
        }
    }
}