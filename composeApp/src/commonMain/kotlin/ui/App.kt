package ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.components.MainContent
import ui.components.base.BaseModalBottomSheet
import ui.components.main.AppTopBar
import ui.components.main.BottomNavigationBar
import ui.components.main.rememberAppState
import ui.screens.MainViewModel
import ui.screens.SheetType.ABOUT_US
import ui.screens.SheetType.NEWS_FILTER
import ui.screens.SheetType.PRIVACY_POLICY
import ui.screens.SheetType.SUBSCRIBE
import ui.screens.initial.landing.privacy_policy.PrivacyPolicySection
import ui.screens.main.MainState.LoggedIn
import ui.screens.main.news.NewsFilterSection
import ui.screens.main.overview.OverviewViewModel
import ui.screens.main.settings.AboutUsSection
import ui.screens.main.subscribers.SubscribersSection
import ui.theme.AppM3Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
internal fun App(
    mainViewModel: MainViewModel = koinViewModel(),
    overviewViewModel: OverviewViewModel = koinViewModel(),
) {
    val navController = rememberNavController()
    val appState = rememberAppState(navController)
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scope = rememberCoroutineScope()

    val mainState by mainViewModel.viewState.collectAsStateWithLifecycle()
    val isDarkMode by mainViewModel.isDark.collectAsStateWithLifecycle()
    val isLoggedIn = mainState is LoggedIn

    val currentDestination = appState.currentDestination
    val hazeState = remember { HazeState() }

    AppM3Theme(isDarkMode = isDarkMode) {
        Scaffold(
            topBar = {
                AppTopBar(
                    currentDestination = currentDestination,
                    navController = navController,
                    scrollBehavior = scrollBehavior,
                    hazeState = hazeState,
                    isLoggedIn = isLoggedIn,
                    onFilterClick = { mainViewModel.toggleSheet(NEWS_FILTER) }
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    currentDestination = currentDestination,
                    hazeState = hazeState,
                    onTabSelected = { tab -> appState.navigateToTopLevelDestination(tab) }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { paddingValues ->
            MainContent(
                appState = appState,
                paddingValues = paddingValues,
                hazeState = hazeState,
                navController = navController,
                isLoggedIn = isLoggedIn,
                mainViewModel = mainViewModel,
                overviewViewModel = overviewViewModel,
                snackbarHostState = snackbarHostState,
                scope = scope
            )
        }

        BottomSheets(mainViewModel = mainViewModel)
    }
}

@Composable
private fun BottomSheets(
    mainViewModel: MainViewModel
) {
    BaseModalBottomSheet(
        isVisible = mainViewModel.isSubscribeSheetVisible,
        onDismiss = { mainViewModel.toggleSheet(SUBSCRIBE) }) { SubscribersSection() }
    BaseModalBottomSheet(
        isVisible = mainViewModel.isPrivacyPolicySheetVisible,
        onDismiss = { mainViewModel.toggleSheet(PRIVACY_POLICY) }) { PrivacyPolicySection() }
    BaseModalBottomSheet(
        isVisible = mainViewModel.isAboutUsSheetVisible,
        onDismiss = { mainViewModel.toggleSheet(ABOUT_US) }) { AboutUsSection() }
    BaseModalBottomSheet(
        isVisible = mainViewModel.isNewsFilterSheetVisible,
        onDismiss = { mainViewModel.toggleSheet(NEWS_FILTER) }) {
        NewsFilterSection(
            onCloseClick = { mainViewModel.toggleSheet(NEWS_FILTER) }
        )
    }
}
