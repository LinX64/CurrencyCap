package ui.screens.auth.forgot_password

import androidx.lifecycle.viewModelScope
import domain.repository.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.auth.forgot_password.ResetPasswordViewEvent.OnEmailChanged
import ui.screens.auth.forgot_password.ResetPasswordViewEvent.OnResetPasswordClick

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

        viewModelScope.launch {
            authService.sendRecoveryEmail(email)
            setEffect(ResetPasswordNavigationEffect.NavigateToLogin)
        }
    }
}
