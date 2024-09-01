package ui.screens.initial.reset_password

import androidx.lifecycle.viewModelScope
import domain.repository.AuthServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.initial.reset_password.ResetPasswordNavigationEffect.ShowError
import ui.screens.initial.reset_password.ResetPasswordState.Error
import ui.screens.initial.reset_password.ResetPasswordState.Loading
import ui.screens.initial.reset_password.ResetPasswordState.Success
import ui.screens.initial.reset_password.ResetPasswordViewEvent.OnEmailChanged
import ui.screens.initial.reset_password.ResetPasswordViewEvent.OnResetPasswordClick
import util.isEmailValid

internal class ResetPasswordViewModel(
    private val authServiceRepository: AuthServiceRepository
) : MviViewModel<ResetPasswordViewEvent, ResetPasswordState, ResetPasswordNavigationEffect>(ResetPasswordState.Idle) {

    val email: MutableStateFlow<String> = MutableStateFlow("")

    override fun handleEvent(event: ResetPasswordViewEvent) {
        when (event) {
            is OnEmailChanged -> email.value = event.email.trim()
            is OnResetPasswordClick -> handleResetPasswordClick()
        }
    }

    private fun handleResetPasswordClick() {
        val email = email.value

        when {
            email.isEmpty() -> setState { Error("Email cannot be empty!") }
            email.isEmailValid().not() -> setEffect(ShowError("Invalid email!"))
            else -> sendRecoveryEmail(email)
        }
    }

    private fun sendRecoveryEmail(email: String) {
        setState { Loading }

        viewModelScope.launch {
            authServiceRepository.sendRecoveryEmail(email)
            setState { Success }
        }
    }
}
