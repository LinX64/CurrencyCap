package ui.screens.auth.fill_profile

import androidx.lifecycle.viewModelScope
import data.model.User
import domain.repository.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.auth.fill_profile.FillProfileNavigationEffect.NavigateToMarketOverview
import ui.screens.auth.fill_profile.FillProfileViewEvent.EmailChanged
import ui.screens.auth.fill_profile.FillProfileViewEvent.OnSignUpClick
import ui.screens.auth.fill_profile.FillProfileViewEvent.OnSkipClicked
import ui.screens.auth.fill_profile.FillProfileViewEvent.PasswordChanged

internal class FillProfileViewModel(
    private val authService: AuthService
) : MviViewModel<FillProfileViewEvent, FillProfileState, FillProfileNavigationEffect>(FillProfileState.Idle) {

    val email: MutableStateFlow<String> = MutableStateFlow("")
    val password: MutableStateFlow<String> = MutableStateFlow("")

    override fun handleEvent(event: FillProfileViewEvent) {
        when (event) {
            is EmailChanged -> email.value = event.email
            is PasswordChanged -> password.value = event.password
            OnSignUpClick -> onSignUpFinishClicked()
            OnSkipClicked -> setEffect(NavigateToMarketOverview)
        }
    }

    private fun onSignUpFinishClicked() {
        setState { FillProfileState.Loading }

        val updatedUser = User(
            email = email.value,
            photoUrl = ""
        )

        viewModelScope.launch {
            authService.updateCurrentUser(updatedUser)
        }

        setEffect(NavigateToMarketOverview)
    }
}
