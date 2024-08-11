package ui.screens.initial.fill_profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import data.remote.model.User
import domain.repository.ProfileRepository
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.initial.fill_profile.FillProfileNavigationEffect.NavigateToMarketOverview
import ui.screens.initial.fill_profile.FillProfileNavigationEffect.ShowError
import ui.screens.initial.fill_profile.FillProfileState.Idle
import ui.screens.initial.fill_profile.FillProfileState.Loading
import ui.screens.initial.fill_profile.FillProfileViewEvent.OnNameChanged
import ui.screens.initial.fill_profile.FillProfileViewEvent.OnPasswordError
import ui.screens.initial.fill_profile.FillProfileViewEvent.OnPhoneNumberChanged
import ui.screens.initial.fill_profile.FillProfileViewEvent.OnSignUpClick
import ui.screens.initial.fill_profile.FillProfileViewEvent.OnSkipClicked

internal class FillProfileViewModel(
    private val profileRepository: ProfileRepository
) : MviViewModel<FillProfileViewEvent, FillProfileState, FillProfileNavigationEffect>(Idle) {

    private val fullName = mutableStateOf("")
    private val phoneNumber = mutableStateOf("")

    override fun handleEvent(event: FillProfileViewEvent) {
        when (event) {
            is OnNameChanged -> fullName.value = event.fullName
            is OnPhoneNumberChanged -> phoneNumber.value = event.phoneNumber
            is OnPasswordError -> setEffect(ShowError(event.message))
            OnSignUpClick -> onSignUpFinishClicked()
            OnSkipClicked -> setEffect(NavigateToMarketOverview)
        }
    }

    private fun onSignUpFinishClicked() {
        setState { Loading }

        if (fullName.value.isEmpty() || phoneNumber.value.isEmpty()) {
            setEffect(ShowError("Please fill all fields"))
            return
        }

        val updatedUser = User(
            fullName = fullName.value,
            phoneNumber = phoneNumber.value
        )

        viewModelScope.launch {
            profileRepository.saveUserProfile(updatedUser)
        }

        setState { Idle }
        setEffect(NavigateToMarketOverview)
    }
}
