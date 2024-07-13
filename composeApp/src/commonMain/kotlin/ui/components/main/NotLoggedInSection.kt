package ui.components.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.components.PrivacyPolicyBottomSheet
import ui.navigation.graphs.AuthNavGraph
import ui.navigation.util.ScreenRoutes
import ui.screens.MainViewModel
import ui.screens.initial.privacy_policy.PrivacyPolicySection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NotLoggedInSection(
    appState: AppState = rememberAppState(),
    scope: CoroutineScope,
    mainViewModel: MainViewModel,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val hazeState = remember { HazeState() }
    val currentDestination = appState.currentDestination
    val navController = appState.navController
    val isSheetOpen = rememberSaveable { mutableStateOf(false) }
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
            showPrivacyPolicyBottomSheet = { isSheetOpen.value = true }
        )
    }

    if (isSheetOpen.value) {
        PrivacyPolicyBottomSheet(
            isVisible = isSheetOpen.value,
            onDismissRequest = { isSheetOpen.value = false }
        ) { PrivacyPolicySection() }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NotLoggedInTopAppBar(
    currentDestination: String?,
    navController: NavHostController,
    hazeState: HazeState,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth()
            .hazeChild(state = hazeState),
        title = { },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        navigationIcon = { NavigationIcon(currentDestination, navController) },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun NavigationIcon(
    currentDestination: String?,
    navController: NavHostController
) {
    currentDestination ?: return

    if (isNavigationIconVisible(currentDestination)) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }
    }
}

private fun isNavigationIconVisible(currentDestination: String) = (currentDestination != ScreenRoutes.LANDING)
        && (currentDestination != ScreenRoutes.LOGIN)
        && (currentDestination != ScreenRoutes.REGISTER)

