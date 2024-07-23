package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import di.koinViewModel
import ui.components.main.LoggedInSection
import ui.components.main.NotLoggedInSection
import ui.screens.MainState.Loading
import ui.screens.MainState.LoggedIn
import ui.screens.MainState.NotLoggedIn
import ui.screens.MainViewModel
import ui.screens.initial.splash.SplashScreen

@Composable
internal fun App(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
) {
    val mainState by mainViewModel.appState.collectAsStateWithLifecycle()
    when (mainState) {
        Loading -> SplashScreen()
        is LoggedIn -> LoggedInSection(mainViewModel = mainViewModel)
        is NotLoggedIn -> NotLoggedInSection(mainViewModel = mainViewModel)
        else -> Unit
    }

    DisposableEffect(Unit) {
        onDispose {
            mainViewModel.clearState()
        }
    }
}