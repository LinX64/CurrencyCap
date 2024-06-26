package ui.screens.news.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import ui.navigation.NavRoutes
import ui.screens.news.NewsScreen

fun NavController.navigateToNewsScreen() = navigate(NavRoutes.NEWS)

fun NavGraphBuilder.newsScreen(padding: PaddingValues, hazeState: HazeState) {
    composable(NavRoutes.NEWS) {
        NewsScreen(padding = padding, hazeState = hazeState)
    }
}
