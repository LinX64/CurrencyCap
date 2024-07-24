package ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.components.base.BaseModalBottomSheet
import ui.components.main.AppState
import ui.components.main.AppTopBar
import ui.components.main.BottomNavigationBar
import ui.components.main.rememberAppState
import ui.navigation.graphs.AppNavGraph
import ui.screens.MainState
import ui.screens.MainViewModel
import ui.screens.initial.landing.privacy_policy.PrivacyPolicySection
import ui.screens.initial.splash.SplashScreen
import ui.screens.main.news.NewsViewEvent.OnSetClick
import ui.screens.main.news.NewsViewModel
import ui.screens.main.news.components.NewsFilterSection
import ui.screens.main.overview.navigation.Overview
import ui.screens.main.subscribers.SubscribersSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
    newsViewModel: NewsViewModel = koinViewModel<NewsViewModel>(),
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope(),
    appState: AppState = rememberAppState(),
) {
    val mainState by mainViewModel.appState.collectAsState()
    val currentDestination = appState.currentDestination
    val navController = appState.navController
    val isLoggedIn = mainState is MainState.LoggedIn
    val isLoading = mainState is MainState.Loading

    val hazeState = remember { HazeState() }
    var isNewsFilterSheetVisible by remember { mutableStateOf(false) }
    var isSheetOpen by remember { mutableStateOf(false) }
    var isSubscribeSheetVisible by remember { mutableStateOf(false) }

    if (isLoading) SplashScreen()

    Scaffold(
        topBar = {
            AppTopBar(
                currentDestination = currentDestination,
                hazeState = hazeState,
                isLoggedIn = isLoggedIn,
                navController = navController,
                scrollBehavior = scrollBehavior,
                onFilterClick = { isNewsFilterSheetVisible = true }
            )
        },
        bottomBar = {
            if (isLoggedIn) {
                BottomNavigationBar(
                    currentDestination = currentDestination,
                    scrollBehavior = scrollBehavior,
                    hazeState = hazeState,
                    onTabSelected = { tab -> appState.navigateToTopLevelDestination(tab) }
                )
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        AppNavGraph(
            navController = navController,
            padding = paddingValues,
            hazeState = hazeState,
            isLoggedIn = isLoggedIn,
            scrollBehavior = scrollBehavior,
            onNavigateToLanding = mainViewModel::navigateToLanding,
            onError = { message -> scope.launch { snackbarHostState.showSnackbar(message) } },
            onLoginSuccess = {
                mainViewModel.onLoginSuccess()
                navController.navigate(Overview)
            },
            showPrivacyPolicyBottomSheet = { isSheetOpen = true }
        )
    }

    BaseModalBottomSheet(
        isVisible = isSubscribeSheetVisible,
        onDismiss = { isSubscribeSheetVisible = false }
    ) { SubscribersSection() }

    BaseModalBottomSheet(
        isVisible = isNewsFilterSheetVisible,
        onDismiss = { isNewsFilterSheetVisible = false }
    ) {
        NewsFilterSection(
            sources = newsViewModel.sources.value,
            onCloseClick = { isNewsFilterSheetVisible = false },
            onDoneClick = { startDate, endDate, selectedSources ->
                isNewsFilterSheetVisible = false
                newsViewModel.handleEvent(OnSetClick(startDate, endDate, selectedSources))
            }
        )
    }

    BaseModalBottomSheet(
        isVisible = isSheetOpen,
        onDismiss = { isSheetOpen = false }
    ) { PrivacyPolicySection() }
}

