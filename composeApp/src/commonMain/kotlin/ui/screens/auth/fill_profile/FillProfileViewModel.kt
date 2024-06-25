package ui.screens.auth.fill_profile

import androidx.lifecycle.viewModelScope
import data.model.User
import domain.repository.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.auth.fill_profile.FillProfileNavigationEffect.NavigateToMarketOverview
import ui.screens.auth.fill_profile.FillProfileViewEvent.OnNameChanged
import ui.screens.auth.fill_profile.FillProfileViewEvent.OnPhoneNumberChanged
import ui.screens.auth.fill_profile.FillProfileViewEvent.OnSignUpClick
import ui.screens.auth.fill_profile.FillProfileViewEvent.OnSkipClicked

internal class FillProfileViewModel(
    private val authService: AuthService
) : MviViewModel<FillProfileViewEvent, FillProfileState, FillProfileNavigationEffect>(FillProfileState.Idle) {

    val name: MutableStateFlow<String> = MutableStateFlow("")
    val phone: MutableStateFlow<String> = MutableStateFlow("")

    override fun handleEvent(event: FillProfileViewEvent) {
        when (event) {
            is OnNameChanged -> Unit
            is OnPhoneNumberChanged -> Unit
            OnSignUpClick -> onSignUpFinishClicked()
            OnSkipClicked -> setEffect(NavigateToMarketOverview)
        }
    }

    private fun onSignUpFinishClicked() {
        setState { FillProfileState.Loading }

        val updatedUser = User(
            name = name.value,
            phoneNumber = phone.value
        )

        viewModelScope.launch {
            authService.updateCurrentUser(updatedUser)
            authService
        }

        setEffect(NavigateToMarketOverview)
        setState { FillProfileState.Idle }
    }
}
