package ui.screens.initial.reset_password

import androidx.lifecycle.viewModelScope
import com.mvicompose.linx64.ui.MviViewModel
import domain.repository.AuthServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.screens.initial.reset_password.ResetPasswordViewEvent.OnEmailChanged
import ui.screens.initial.reset_password.ResetPasswordViewEvent.OnResetPasswordClick
import util.validateEmail

internal class ResetPasswordViewModel(
    private val authServiceRepository: AuthServiceRepository
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
            authServiceRepository.sendRecoveryEmail(email)

            setState { ResetPasswordState.Success }
        }
    }
}
