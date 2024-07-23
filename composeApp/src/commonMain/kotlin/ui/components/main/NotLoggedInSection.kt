package ui.components.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.components.NotLoggedInTopAppBar
import ui.components.base.BaseModalBottomSheet
import ui.navigation.graphs.AuthNavGraph
import ui.screens.MainViewModel
import ui.screens.initial.landing.privacy_policy.PrivacyPolicySection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NotLoggedInSection(
    appState: AppState = rememberAppState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    mainViewModel: MainViewModel,
) {
    val hazeState = remember { HazeState() }
    var isSheetOpen by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = appState.navController
    val currentDestination = appState.currentDestination
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            NotLoggedInTopAppBar(
                currentDestination = currentDestination,
                navController = navController,
                hazeState = hazeState,
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        AuthNavGraph(
            navController = navController,
            padding = paddingValues,
            onLoginSuccess = {
                mainViewModel.onLoginSuccess()
                navController.popBackStack()
            },
            onError = { message -> scope.launch { snackbarHostState.showSnackbar(message) } },
            showPrivacyPolicyBottomSheet = { isSheetOpen = true }
        )
    }

    BaseModalBottomSheet(
        isVisible = isSheetOpen,
        onDismiss = { isSheetOpen = false }
    ) {
        PrivacyPolicySection()
    }
}