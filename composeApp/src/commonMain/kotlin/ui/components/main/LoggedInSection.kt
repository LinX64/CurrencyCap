package ui.components.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.components.BottomSheet
import ui.navigation.graphs.MainNavGraph
import ui.navigation.util.NavRoutes
import ui.screens.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoggedInSection(
    appState: LoggedInAppState = rememberLoggedInAppState(),
    mainViewModel: MainViewModel,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    scope: CoroutineScope
) {
    val currentDestination = appState.currentDestination?.route
    val navController = appState.navController
    val isSheetOpen = rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val hazeState = remember { HazeState() }
    val isSettingsScreen = currentDestination == NavRoutes.SETTINGS

    Scaffold(
        topBar = {
            AppTopBar(
                currentDestination = currentDestination,
                hazeState = hazeState,
                navController = navController,
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                scrollBehavior = scrollBehavior,
                hazeState = hazeState,
                isSettingsScreen = isSettingsScreen,
                onTabSelected = { tab -> appState.navigateToTopLevelDestination(tab) }
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        MainNavGraph(
            navController = navController,
            padding = paddingValues,
            hazeState = hazeState,
            scrollBehavior = scrollBehavior,
            onNavigateToLanding = mainViewModel::navigateToLanding,
            onError = { message -> scope.launch { snackbarHostState.showSnackbar(message) } }
        )
    }

    if (isSheetOpen.value) BottomSheet(sheetState = scaffoldState)
}