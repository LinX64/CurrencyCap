package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ui.components.base.BaseModalBottomSheet
import ui.components.main.AppState
import ui.components.main.AppTopBar
import ui.components.main.BottomBarTab
import ui.components.main.BottomNavigationBar
import ui.components.main.rememberAppState
import ui.navigation.graphs.AppNavGraph
import ui.screens.MainState
import ui.screens.MainViewModel
import ui.screens.initial.landing.navigation.Landing
import ui.screens.initial.landing.privacy_policy.PrivacyPolicySection
import ui.screens.main.news.NewsViewEvent.OnSetClick
import ui.screens.main.news.NewsViewModel
import ui.screens.main.news.components.NewsFilterSection
import ui.screens.main.subscribers.SubscribersSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun App(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
    newsViewModel: NewsViewModel = koinViewModel<NewsViewModel>(),
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    val navController = rememberNavController()
    val appState: AppState = rememberAppState(navController)

    val mainState by mainViewModel.appState.collectAsState()
    val currentDestination = appState.currentDestination
    val isLoggedIn = mainState is MainState.LoggedIn

    val hazeState = remember { HazeState() }
    var isNewsFilterSheetVisible by remember { mutableStateOf(false) }
    var isSheetOpen by remember { mutableStateOf(false) }
    var isSubscribeSheetVisible by remember { mutableStateOf(false) }

    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val onRefresh: () -> Unit = {
        isRefreshing = true
        scope.launch {
            delay(1500)
            isRefreshing = false
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                currentDestination = currentDestination,
                navController = navController,
                scrollBehavior = scrollBehavior,
                isLoggedIn = isLoggedIn,
                hazeState = hazeState,
                onFilterClick = { isNewsFilterSheetVisible = true },
                onThemeChangeClick = mainViewModel::toggleDarkMode
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentDestination = currentDestination,
                scrollBehavior = scrollBehavior,
                isLoggedIn = isLoggedIn,
                hazeState = hazeState,
                onTabSelected = { tab -> appState.navigateToTopLevelDestination(tab) }
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .pullToRefresh(
                    state = pullToRefreshState,
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        isRefreshing = true
                        onRefresh()
                    },
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            AppNavGraph(
                navController = navController,
                hazeState = hazeState,
                isLoggedIn = isLoggedIn,
                onNavigateToLanding = { navigateToLanding(mainViewModel, navController) },
                showPrivacyPolicyBottomSheet = { isSheetOpen = true },
                onError = { message -> scope.launch { snackbarHostState.showSnackbar(message) } },
                onLoginSuccess = { navigateToOverview(mainViewModel, navController) },
                onExploreNewsClick = { appState.navigateToTopLevelDestination(BottomBarTab.News) }
            )

            PullToRefreshBox(
                state = pullToRefreshState,
                isRefreshing = isRefreshing,
                onRefresh = {
                    isRefreshing = true
                    onRefresh()
                },
                content = {}
            )
        }
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

private fun navigateToOverview(
    mainViewModel: MainViewModel,
    navController: NavHostController
) {
    mainViewModel.onLoginSuccess()
    navController.popBackStack()
}

private fun navigateToLanding(
    mainViewModel: MainViewModel,
    navController: NavHostController
) {
    mainViewModel.navigateToLanding()
    navController.navigate(Landing) {
        popUpTo(navController.graph.startDestinationId) {
            inclusive = true
        }
    }
}

