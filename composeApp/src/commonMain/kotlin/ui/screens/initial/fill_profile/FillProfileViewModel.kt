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
            authServiceRepository.updateCurrentUser(updatedUser)
            authServiceRepository
        }

        setEffect(NavigateToMarketOverview)
        setState { FillProfileState.Idle }
    }
}
