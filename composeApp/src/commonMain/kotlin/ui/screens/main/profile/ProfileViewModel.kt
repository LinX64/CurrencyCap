package ui.screens.main.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.mvicompose.linx64.ui.MviViewModel
import data.remote.model.User
import domain.repository.AuthServiceRepository
import domain.repository.ProfileRepository
import domain.repository.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import ui.screens.main.profile.ProfileNavigationEffect.NavigateToLanding
import ui.screens.main.profile.ProfileNavigationEffect.OpenEmailApp
import ui.screens.main.profile.ProfileState.Idle
import ui.screens.main.profile.ProfileState.Loading
import ui.screens.main.profile.ProfileState.Success
import ui.screens.main.profile.ProfileViewEvent.OnDeleteAccountCardClicked
import ui.screens.main.profile.ProfileViewEvent.OnFetchProfile
import ui.screens.main.profile.ProfileViewEvent.OnSignOutClicked
import ui.screens.main.profile.ProfileViewEvent.OnSupportClicked

internal class ProfileViewModel(
    private val authServiceRepository: AuthServiceRepository,
    private val userPreferences: UserPreferences,
    private val profileRepository: ProfileRepository
) : MviViewModel<ProfileViewEvent, ProfileState, ProfileNavigationEffect>(Idle) {

    val uid: MutableState<String> = mutableStateOf("")

    init {
        handleEvent(OnFetchProfile)
    }

    override fun handleEvent(event: ProfileViewEvent) {
        when (event) {
            OnFetchProfile -> fetchProfile()
            OnSupportClicked -> setEffect(OpenEmailApp)
            OnSignOutClicked -> logout()
            is OnDeleteAccountCardClicked -> handleDeleteAccount()
        }
    }

    private fun handleDeleteAccount() {
        setState { Loading }

        viewModelScope.launch {
            userPreferences.clear()
            authServiceRepository.deleteAccount()

            setState { Idle }
            setEffect(NavigateToLanding)
        }
    }

    private fun fetchProfile() {
        setState { Loading }
        val user = authServiceRepository.currentUser

        viewModelScope.launch {
            val fullName = profileRepository.getUserFullName()
            val phoneNumber = profileRepository.getUserPhoneNumber()
            val email = user.mapNotNull { it.email }.first()

            setState {
                Success(
                    User(
                        fullName = fullName,
                        phoneNumber = phoneNumber,
                        email = email
                    )
                )
            }
        }
    }

    private fun logout() {
        setState { Loading }

        viewModelScope.launch {
            userPreferences.clear()
            setState { Idle }

            setEffect(NavigateToLanding)
        }
    }
}