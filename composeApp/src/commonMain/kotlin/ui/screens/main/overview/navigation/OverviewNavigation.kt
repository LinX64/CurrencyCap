package ui.screens.main.overview.navigation

import CryptoList
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import ui.screens.main.ai_predict.navigation.navigateToAiPredictScreen
import ui.screens.main.detail.navigation.CryptoDetail
import ui.screens.main.news.news_detail.navigation.NewsDetail
import ui.screens.main.overview.OverviewRoute
import ui.screens.main.search.navigation.Explore
import ui.screens.main.top_rates.navigation.TopRates

fun NavController.navigateToOverviewScreen(navOptions: NavOptions) = navigate(Overview, navOptions)

fun NavGraphBuilder.overviewScreen(
    hazeState: HazeState,
    navController: NavHostController,
) {
    composable<Overview>(
        enterTransition = {
            slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight / 10 },
                animationSpec = tween(durationMillis = 300)
            ) + fadeIn(animationSpec = tween(durationMillis = 300)) +
                    scaleIn(
                        initialScale = 0.9f,
                        animationSpec = tween(durationMillis = 300)
                    )
        },
    ) {
        OverviewRoute(
            hazeState = hazeState,
            onSearchCardClicked = { navController.navigate(Explore) },
            onViewAllClick = { navController.navigate(CryptoList) },
            onViewAllTopRatesClick = { navController.navigate(TopRates) },
            onNewsItemClick = { url ->
                val encodedUrl = UrlEncoderUtil.encode(url)
                navController.navigate(NewsDetail(encodedUrl))
            },
            onCircleButtonClicked = { navController.navigateToAiPredictScreen() },
            onCryptoItemClick = { id, symbol -> navController.navigate(CryptoDetail(id, symbol)) }
        )
    }
}

@Serializable
data object Overview
