package ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import dev.chrisbanes.haze.HazeState
import ui.screens.initial.fill_profile.navigation.fillProfileScreen
import ui.screens.initial.get_verified.navigation.getVerifiedPhoneScreen
import ui.screens.initial.landing.navigation.Landing
import ui.screens.initial.landing.navigation.landingScreen
import ui.screens.initial.login.navigation.loginScreen
import ui.screens.initial.register.navigation.registerScreen
import ui.screens.initial.reset_password.navigation.resetPasswordScreen
import ui.screens.main.ai_predict.navigation.aiPredictScreen
import ui.screens.main.bookmarks.navigation.bookmarksScreen
import ui.screens.main.detail.navigation.detailScreen
import ui.screens.main.exchange.navigation.exchangeScreen
import ui.screens.main.news.navigation.newsScreen
import ui.screens.main.news.news_detail.navigation.newsDetailScreen
import ui.screens.main.overview.navigation.Overview
import ui.screens.main.overview.navigation.overviewScreen
import ui.screens.main.profile.navigation.profileScreen
import ui.screens.main.search.navigation.searchScreen
import ui.screens.main.settings.navigation.settingsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainNavGraph(
    navController: NavHostController,
    padding: PaddingValues,
    hazeState: HazeState,
    scrollBehavior: TopAppBarScrollBehavior,
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Overview,
        modifier = Modifier
            .consumeWindowInsets(padding)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        authNavGraph(
            padding = padding,
            navController = navController,
            onError = onError,
            showPrivacyPolicyBottomSheet = { /* no-op */ },
            onLoginSuccess = { /* no-op */ }
        )

        mainNavGraph(
            padding = padding,
            navController = navController,
            hazeState = hazeState,
            onNavigateToLanding = onNavigateToLanding,
            onError = onError
        )
    }
}

private fun NavGraphBuilder.mainNavGraph(
    padding: PaddingValues,
    navController: NavHostController,
    hazeState: HazeState,
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit
) {
    searchScreen(
        padding = padding,
        navController = navController,
        hazeState = hazeState
    )

    aiPredictScreen(
        padding = padding,
        hazeState = hazeState
    )

    overviewScreen(
        padding = padding,
        hazeState = hazeState,
        navController = navController
    )

    bookmarksScreen(
        padding = padding,
        hazeState = hazeState,
        navController = navController
    )

    newsScreen(
        padding = padding,
        hazeState = hazeState,
        navController = navController
    )

    newsDetailScreen(
        padding = padding,
        hazeState = hazeState,
        onError = onError
    )

    detailScreen(
        padding = padding,
        hazeState = hazeState
    )

    exchangeScreen(
        padding = padding,
        onError = onError,
        hazeState = hazeState
    )

    profileScreen(
        padding = padding,
        onNavigateToLanding = onNavigateToLanding,
        onError = onError,
        hazeState = hazeState
    )

    settingsScreen(
        padding = padding,
        hazeState = hazeState
    )
}

private fun NavGraphBuilder.authNavGraph(
    padding: PaddingValues,
    navController: NavHostController,
    showPrivacyPolicyBottomSheet: () -> Unit,
    onLoginSuccess: () -> Unit,
    onError: (message: String) -> Unit
) {
    navigation<Landing>(startDestination = Landing) {
        landingScreen(
            navController = navController,
            showPrivacyPolicyBottomSheet = showPrivacyPolicyBottomSheet
        )

        loginScreen(
            padding = padding,
            navController = navController,
            onLoginSuccess = onLoginSuccess,
            onError = onError
        )

        registerScreen(
            navController = navController,
            onError = onError,
            onTermsOfServiceClick = showPrivacyPolicyBottomSheet
        )

        fillProfileScreen(
            padding = padding,
            onNavigateToMarketOverview = onLoginSuccess,
            onError = onError
        )

        getVerifiedPhoneScreen(
            padding = padding,
            onNavigateToMarketOverview = onLoginSuccess,
            onError = onError
        )

        resetPasswordScreen(
            navController = navController,
            padding = padding,
            onError = onError
        )
    }
}
