package ui.screens.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import data.model.User
import domain.repository.AuthService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ui.common.MviViewModel

class ProfileViewModel(
    private val authService: AuthService
) : MviViewModel<ProfileViewEvent, ProfileState, ProfileNavigationEffect>(ProfileState.Idle) {

    val name: MutableState<String> = mutableStateOf("")
    val email: MutableState<String> = mutableStateOf("")
    val phone: MutableState<String> = mutableStateOf("")
    val uid: MutableState<String> = mutableStateOf("")

    init {
        fetchProfile()
    }

    override fun handleEvent(event: ProfileViewEvent) {
        when (event) {
            is ProfileViewEvent.OnProfileCardClicked -> {

            }

            is ProfileViewEvent.OnHelpCenterCardClicked -> {

            }

            is ProfileViewEvent.OnDeleteAccountCardClicked -> {

            }
        }
    }

    private fun fetchProfile() {
        val user = authService.currentUser
        updateState(user)
    }

    private fun updateState(user: Flow<User>) {
        viewModelScope.launch {
            user.collect {
                name.value = it.name ?: "error"
                email.value = it.email ?: "error"
                phone.value = it.phoneNumber ?: "error"
                uid.value = it.id
            }
        }
    }
}