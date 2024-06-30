package ui.screens.initial.get_verified

import domain.repository.AuthServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import ui.common.MviViewModel
import ui.screens.initial.get_verified.GetVerifiedPhoneState.Error
import ui.screens.initial.get_verified.GetVerifiedPhoneState.Loading
import ui.screens.initial.get_verified.GetVerifiedPhoneViewEvent.OnCodeChanged
import ui.screens.initial.get_verified.GetVerifiedPhoneViewEvent.OnFinishSignUpClick

internal class GetVerifiedPhoneViewModel(
    private val authServiceRepository: AuthServiceRepository
) : MviViewModel<GetVerifiedPhoneViewEvent, GetVerifiedPhoneState, GetVerifiedPhoneNavigationEffect>(Loading) {

    private val code: MutableStateFlow<String> = MutableStateFlow("")

    override fun handleEvent(event: GetVerifiedPhoneViewEvent) {
        when (event) {
            is OnCodeChanged -> code.value = event.code
            is OnFinishSignUpClick -> onSignUpFinishClicked()
        }
    }

    private fun onSignUpFinishClicked() {
        setState { Loading }

        if (code.value.length != 6) {
            setState { Error("Invalid code") }
            return
        }

        // TODO: Implement the logic to verify the code
    }
}
