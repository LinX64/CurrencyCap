package ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.components.AppTopBar
import ui.components.BottomNavigationBar
import ui.components.CenteredColumn
import ui.components.SubscribeBottomSheet
import ui.navigation.graphs.AuthNavGraph
import ui.navigation.graphs.MainNavGraph
import ui.navigation.graphs.handleNavigation
import ui.screens.MainState
import ui.screens.MainViewModel
import ui.screens.subscribers.SubscribersSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
    navController: NavHostController = rememberNavController(),
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    hazeState: HazeState = remember { HazeState() },
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val mainState by mainViewModel.state.collectAsState()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route ?: ""
    val isSheetOpen = rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            AppTopBar(
                currentDestination = currentDestination,
                navController = navController,
                hazeState = hazeState,
                scrollBehavior = scrollBehavior,
                onLogoutClick = { mainViewModel.logout() }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                hazeState = hazeState,
                currentDestination = currentDestination,
                onTabSelected = { tab ->
                    handleNavigation(
                        navController = navController,
                        tab = tab,
                        isSheetOpen = isSheetOpen
                    )
                }
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        checkUserStatus(
            mainState = mainState,
            navController = navController,
            paddingValues = paddingValues,
            scrollBehavior = scrollBehavior,
            onError = { message -> scope.launch { snackbarHostState.showSnackbar(message) } }
        )
    }

    if (isSheetOpen.value) {
        SubscribeBottomSheet(sheetState = scaffoldState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun checkUserStatus(
    mainState: MainState,
    navController: NavHostController,
    paddingValues: PaddingValues,
    scrollBehavior: TopAppBarScrollBehavior,
    onError: (message: String) -> Unit
) = when (mainState) {
    is MainState.Loading -> CenteredColumn { CircularProgressIndicator() }
    is MainState.LoggedIn -> {
        MainNavGraph(
            navController = navController,
            padding = paddingValues,
            scrollBehavior = scrollBehavior
        )
    }

    is MainState.NotLoggedIn -> {
        AuthNavGraph(
            padding = paddingValues,
            navController = navController,
            onError = onError
        )
    }

    else -> Unit
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubscribeBottomSheet(sheetState: BottomSheetScaffoldState) {
    SubscribeBottomSheet(scaffoldState = sheetState) {
        SubscribersSection()
    }
}
