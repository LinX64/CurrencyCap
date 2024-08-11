package ui.screens.initial.register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import data.remote.repository.auth.AuthServiceRepositoryImpl.AuthState
import domain.repository.AuthServiceRepository
import domain.repository.UserPreferences
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.initial.register.RegisterNavigationEffect.NavigateToFillProfile
import ui.screens.initial.register.RegisterNavigationEffect.ShowError
import ui.screens.initial.register.RegisterState.Loading
import ui.screens.initial.register.RegisterViewEvent.OnEmailChanged
import ui.screens.initial.register.RegisterViewEvent.OnPasswordChanged
import ui.screens.initial.register.RegisterViewEvent.OnRegisterClick

internal class RegisterViewModel(
    private val authServiceRepository: AuthServiceRepository,
    private val userPreferences: UserPreferences
) : MviViewModel<RegisterViewEvent, RegisterState, RegisterNavigationEffect>(RegisterState.Idle) {

    val email: MutableState<String> = mutableStateOf("")
    private val password: MutableState<String> = mutableStateOf("")

    override fun handleEvent(event: RegisterViewEvent) {
        when (event) {
            is OnEmailChanged -> email.value = event.newEmail
            is OnPasswordChanged -> password.value = event.newPassword
            is OnRegisterClick -> register(email = email.value, password = password.value)
        }
    }

    private fun register(email: String, password: String) {
        when {
            email.isEmpty() -> setEffect(ShowError("Email cannot be empty!"))
            password.isEmpty() -> setEffect(ShowError("Password cannot be empty!"))
            else -> registerUser(email, password)
        }
    }

    private fun registerUser(email: String, password: String) {
        setState { Loading }

        viewModelScope.launch {
            when (val state = authServiceRepository.signUpWithEmailAndPassword(email, password)) {
                AuthState.Loading -> Unit
                is AuthState.Success -> {
                    userPreferences.saveUserUid(authServiceRepository.currentUserId)
                    setEffect(NavigateToFillProfile)
                }

                is AuthState.Error -> setEffect(ShowError("Error registering user: ${state.message}"))
            }

            setState { RegisterState.Idle }
        }
    }
}