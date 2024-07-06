package ui.screens.initial.fill_profile

import androidx.lifecycle.viewModelScope
import data.remote.model.User
import domain.repository.AuthServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.initial.fill_profile.FillProfileNavigationEffect.NavigateToMarketOverview
import ui.screens.initial.fill_profile.FillProfileViewEvent.OnNameChanged
import ui.screens.initial.fill_profile.FillProfileViewEvent.OnPhoneNumberChanged
import ui.screens.initial.fill_profile.FillProfileViewEvent.OnSignUpClick
import ui.screens.initial.fill_profile.FillProfileViewEvent.OnSkipClicked

internal class FillProfileViewModel(
    private val authServiceRepository: AuthServiceRepository
) : MviViewModel<FillProfileViewEvent, FillProfileState, FillProfileNavigationEffect>(FillProfileState.Idle) {

    val name: MutableStateFlow<String> = MutableStateFlow("")
    val lastName: MutableStateFlow<String> = MutableStateFlow("")

    override fun handleEvent(event: FillProfileViewEvent) {
        when (event) {
            is OnNameChanged -> name.value = event.name
            is OnPhoneNumberChanged -> lastName.value = event.phoneNumber
            is FillProfileViewEvent.OnLastNameChanged -> lastName.value = event.lastName
            OnSignUpClick -> onSignUpFinishClicked()
            OnSkipClicked -> setEffect(NavigateToMarketOverview)
        }
    }

    private fun onSignUpFinishClicked() {
        setState { FillProfileState.Loading }

        val updatedUser = User(
            name = name.value
        )

        viewModelScope.launch {
            authServiceRepository.updateCurrentUser(updatedUser)
        }

        setState { FillProfileState.Idle }
        //setEffect(NavigateToGetVerifiedPhone(lastName.value))
    }
}
