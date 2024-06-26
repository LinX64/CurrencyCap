package ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import di.koinViewModel
import kotlinx.coroutines.CoroutineScope
import ui.components.main.LoggedInSection
import ui.components.main.NotLoggedInSection
import ui.screens.MainState
import ui.screens.MainViewModel
import ui.screens.splash.SplashScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
    navController: NavHostController = rememberNavController(),
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    val mainState by mainViewModel.state.collectAsStateWithLifecycle()
    when (mainState) {
        MainState.Loading -> SplashScreen()
        is MainState.LoggedIn -> {
            LoggedInSection(
                mainViewModel = mainViewModel,
                navController = navController,
                scope = scope
            )
        }

        is MainState.NotLoggedIn -> {
            NotLoggedInSection(
                navController = navController,
                mainViewModel = mainViewModel,
                scope = scope
            )
        }

        else -> Unit
    }
}

