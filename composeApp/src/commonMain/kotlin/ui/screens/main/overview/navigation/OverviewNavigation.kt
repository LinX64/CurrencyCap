package ui.screens.main.overview.navigation

import androidx.compose.foundation.layout.PaddingValues
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

fun NavController.navigateToOverviewScreen(navOptions: NavOptions) = navigate(Overview, navOptions)

fun NavGraphBuilder.overviewScreen(
    padding: PaddingValues,
    hazeState: HazeState,
    navController: NavHostController
) {
    composable<Overview> {
        OverviewRoute(
            padding = padding,
            hazeState = hazeState,
            onSearchCardClicked = { navController.navigate(Explore) },
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
