package ui.screens.main.news.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import ui.screens.main.news.NewsRoute
import ui.screens.main.news.NewsViewModel
import ui.screens.main.news.news_detail.navigation.NewsDetail

fun NavController.navigateToNewsScreen(navOptions: NavOptions) = navigate(News, navOptions)

fun NavGraphBuilder.newsScreen(
    hazeState: HazeState,
    newsViewModel: NewsViewModel,
    navController: NavHostController,
    showBookmarkConfirmationSnakeBar: (Boolean) -> Unit,
) {
    composable<News> {
        NewsRoute(
            newsViewModel = newsViewModel,
            hazeState = hazeState,
            showBookmarkConfirmationSnakeBar = showBookmarkConfirmationSnakeBar,
            onNewsItemClick = { url ->
                val encodedUrl = UrlEncoderUtil.encode(url)
                navController.navigate(route = NewsDetail(encodedUrl))
            }
        )
    }
}

@Serializable
data object News