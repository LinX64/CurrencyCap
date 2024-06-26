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
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.components.BottomSheet
import ui.navigation.graphs.MainNavGraph
import ui.navigation.graphs.handleNavigation
import ui.screens.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoggedInSection(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    scope: CoroutineScope
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route ?: ""
    val isSheetOpen = rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            AppTopBar(
                currentDestination = currentDestination,
                navController = navController,
                scrollBehavior = scrollBehavior,
                onLogoutClick = { mainViewModel.logout() }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                onTabSelected = { tab ->
                    handleNavigation(
                        navController = navController,
                        tab = tab,
                        isSheetOpen = isSheetOpen
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        MainNavGraph(
            navController = navController,
            padding = paddingValues,
            scrollBehavior = scrollBehavior,
            onNavigateToLanding = { mainViewModel.logout() },
            onError = { message -> scope.launch { snackbarHostState.showSnackbar(message) } }
        )
    }

    if (isSheetOpen.value) BottomSheet(sheetState = scaffoldState)
}