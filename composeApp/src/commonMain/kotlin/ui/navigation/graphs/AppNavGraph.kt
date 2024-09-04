package ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import cryptoListScreen
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
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
import ui.theme.AppDimensions.SPACER_PADDING_32

@Composable
internal fun AppNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    hazeState: HazeState,
    isLoggedIn: Boolean,
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit,
    showPrivacyPolicyBottomSheet: () -> Unit,
    onLoginSuccess: () -> Unit,
    onExploreNewsClick: () -> Unit,
    showBookmarkConfirmationSnakeBar: (Boolean) -> Unit,
    onShowAboutUsBottomSheet: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) MainNavGraph else AuthNavGraph,
        modifier = Modifier
            .fillMaxSize()
            .consumeWindowInsets(paddingValues)
            .padding(bottom = SPACER_PADDING_32)
    ) {
        mainNavGraph(
            navController = navController,
            hazeState = hazeState,
            onError = onError,
            onNavigateToLanding = onNavigateToLanding,
            onExploreNewsClick = onExploreNewsClick,
            showBookmarkConfirmationSnakeBar = showBookmarkConfirmationSnakeBar,
            showPrivacyPolicyBottomSheet = showPrivacyPolicyBottomSheet,
            onShowAboutUsBottomSheet = onShowAboutUsBottomSheet
        )

        authNavGraph(
            navController = navController,
            showPrivacyPolicyBottomSheet = showPrivacyPolicyBottomSheet,
            onLoginSuccess = onLoginSuccess,
            onError = onError
        )
    }
}

private fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    hazeState: HazeState,
    onNavigateToLanding: () -> Unit,
    onError: (message: String) -> Unit,
    onExploreNewsClick: () -> Unit,
    showBookmarkConfirmationSnakeBar: (Boolean) -> Unit,
    onShowAboutUsBottomSheet: () -> Unit,
    showPrivacyPolicyBottomSheet: () -> Unit,
) {
    navigation<MainNavGraph>(startDestination = Overview) {
        searchScreen(
            navController = navController,
            hazeState = hazeState
        )

        aiPredictScreen(
            hazeState = hazeState
        )

        overviewScreen(
            hazeState = hazeState,
            navController = navController,
        )

        bookmarksScreen(
            hazeState = hazeState,
            navController = navController,
            onExploreNewsClick = onExploreNewsClick
        )

        newsScreen(
            hazeState = hazeState,
            navController = navController,
            showBookmarkConfirmationSnakeBar = showBookmarkConfirmationSnakeBar
        )

        newsDetailScreen(
            hazeState = hazeState,
            onError = onError
        )

        detailScreen(
            hazeState = hazeState
        )

        exchangeScreen(
            onError = onError,
            hazeState = hazeState
        )

        profileScreen(
            onNavigateToLanding = onNavigateToLanding,
            onError = onError,
            hazeState = hazeState
        )

        settingsScreen(
            hazeState = hazeState,
            onShowAboutUsBottomSheet = onShowAboutUsBottomSheet,
            onShowPrivacyPolicy = showPrivacyPolicyBottomSheet,
            onError = onError
        )

        cryptoListScreen(
            navController = navController,
            hazeState = hazeState
        )
    }
}

private fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    showPrivacyPolicyBottomSheet: () -> Unit,
    onLoginSuccess: () -> Unit,
    onError: (message: String) -> Unit,
) {
    navigation<AuthNavGraph>(startDestination = Landing) {
        landingScreen(
            navController = navController,
            showPrivacyPolicyBottomSheet = showPrivacyPolicyBottomSheet
        )

        loginScreen(
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
            onNavigateToMarketOverview = onLoginSuccess,
            onError = onError
        )

        getVerifiedPhoneScreen(
            onNavigateToMarketOverview = onLoginSuccess,
            onError = onError
        )

        resetPasswordScreen(
            navController = navController,
            onError = onError
        )
    }
}

@Serializable
data object MainNavGraph

@Serializable
data object AuthNavGraph