package ui.screens.auth.reset_password

import androidx.lifecycle.viewModelScope
import domain.repository.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.auth.reset_password.ResetPasswordViewEvent.OnEmailChanged
import ui.screens.auth.reset_password.ResetPasswordViewEvent.OnResetPasswordClick
import util.validateEmail

internal class ResetPasswordViewModel(
    private val authService: AuthService
) : MviViewModel<ResetPasswordViewEvent, ResetPasswordState, ResetPasswordNavigationEffect>(ResetPasswordState.Idle) {

    val email: MutableStateFlow<String> = MutableStateFlow("")

    override fun handleEvent(event: ResetPasswordViewEvent) {
        when (event) {
            is OnEmailChanged -> email.value = event.email
            is OnResetPasswordClick -> handleResetPasswordClick()
        }
    }

    private fun handleResetPasswordClick() {
        val email = email.value

        if (email.isEmpty()) {
            setState { ResetPasswordState.Error("Email cannot be empty") }
            return
        }

        if (email.validateEmail().not()) {
            setState { ResetPasswordState.Error("Invalid email") }
            return
        }

        viewModelScope.launch {
            authService.sendRecoveryEmail(email)

            setState { ResetPasswordState.Success }
        }
    }
}
