package ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import di.koinViewModel
import kotlinx.coroutines.CoroutineScope
import ui.components.main.LoggedInSection
import ui.components.main.NotLoggedInSection
import ui.screens.MainState
import ui.screens.MainState.LoggedIn
import ui.screens.MainState.NotLoggedIn
import ui.screens.MainViewModel
import ui.screens.initial.splash.SplashScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val mainState by mainViewModel.state.collectAsStateWithLifecycle()
    when (mainState) {
        MainState.Loading -> SplashScreen()
        is LoggedIn -> {
            LoggedInSection(
                mainViewModel = mainViewModel,
                scope = scope
            )
        }

        is NotLoggedIn -> {
            NotLoggedInSection(
                scope = scope,
                mainViewModel = mainViewModel
            )
        }

        else -> Unit
    }
}

