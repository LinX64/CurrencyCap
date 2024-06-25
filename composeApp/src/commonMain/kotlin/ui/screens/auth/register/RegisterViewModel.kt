package ui.screens.auth.register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import data.repository.auth.AuthServiceImpl
import data.repository.datastore.user.UserPreferences
import domain.repository.AuthService
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.auth.register.RegisterState.Loading
import ui.screens.auth.register.RegisterViewEvent.OnEmailChanged
import ui.screens.auth.register.RegisterViewEvent.OnPasswordChanged
import ui.screens.auth.register.RegisterViewEvent.OnRegisterClick

internal class RegisterViewModel(
    private val authService: AuthService,
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
        setState { Loading }

        if (email.isEmpty()) {
            setState { RegisterState.Error("Email cannot be empty!") }
            return
        }

        viewModelScope.launch {
            val state = authService.signUpWithEmailAndPassword(email, password)
            if (state is AuthServiceImpl.AuthState.Success) {
                userPreferences.saveUserUid(authService.currentUserId)
                setEffect(RegisterNavigationEffect.NavigateToFillProfile)
                setState { RegisterState.Idle }

            } else {
                setState { RegisterState.Error("Could not register user!") }
            }
        }
    }
}