package ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.chrisbanes.haze.HazeState
import ui.components.AppTopBar
import ui.components.BottomNavigationBar
import ui.components.SubscribeBottomSheet
import ui.navigation.AppNavigation
import ui.navigation.handleNavigation
import ui.screens.subscribers.SubscribersSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route ?: ""
    val hazeState = remember { HazeState() }
    val isSheetOpen = rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val scaffoldState = rememberBottomSheetScaffoldState()

    Scaffold(
        topBar = {
            AppTopBar(
                currentDestination = currentDestination,
                navController = navController,
                hazeState = hazeState,
                scrollBehavior = scrollBehavior
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
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        AppNavigation(
            navController = navController,
            scrollBehavior = scrollBehavior,
            padding = paddingValues
        )
    }

    if (isSheetOpen.value) {
        SubscribeBottomSheet(sheetState = scaffoldState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SubscribeBottomSheet(sheetState: BottomSheetScaffoldState) {
    SubscribeBottomSheet(scaffoldState = sheetState) {
        SubscribersSection()
    }
}
