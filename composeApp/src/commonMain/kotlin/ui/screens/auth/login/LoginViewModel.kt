package ui.screens.auth.login

import ui.common.MviViewModel

class LoginViewModel(

) : MviViewModel<LoginViewEvent, LoginState, LoginNavigationEffect>(LoginState.Loading) {

    override fun handleEvent(event: LoginViewEvent) {
        when (event) {
            LoginViewEvent.OnLoginClick -> TODO()
        }
    }
}