package ui.components.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.components.base.BaseModelBottomSheet
import ui.navigation.graphs.MainNavGraph
import ui.screens.MainViewModel
import ui.screens.main.news.components.NewsFilterSection
import ui.screens.main.subscribers.SubscribersSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoggedInSection(
    appState: AppState = rememberAppState(),
    mainViewModel: MainViewModel,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope
) {
    val currentDestination = appState.currentDestination
    val navController = appState.navController
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val hazeState = remember { HazeState() }
    val isSubscribeBottomSheetVisible = rememberSaveable { mutableStateOf(false) }
    val isNewsFilterBottomSheetVisible = rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                currentDestination = currentDestination,
                hazeState = hazeState,
                navController = navController,
                scrollBehavior = scrollBehavior,
                onFilterClick = { isNewsFilterBottomSheetVisible.value = true }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentDestination = currentDestination,
                scrollBehavior = scrollBehavior,
                hazeState = hazeState,
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

    BaseModelBottomSheet(
        isVisible = isSubscribeBottomSheetVisible.value,
        onDismiss = { isSubscribeBottomSheetVisible.value = false }
    ) {
        SubscribersSection()
    }

    BaseModelBottomSheet(
        isVisible = isNewsFilterBottomSheetVisible.value,
        onDismiss = { isNewsFilterBottomSheetVisible.value = false }
    ) {
        NewsFilterSection()
    }
}