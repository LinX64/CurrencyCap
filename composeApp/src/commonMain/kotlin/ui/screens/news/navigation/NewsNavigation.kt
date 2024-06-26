package ui.screens.news.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ui.navigation.NavRoutes
import ui.screens.news.NewsScreen

fun NavController.navigateToNewsScreen() = navigate(NavRoutes.NEWS)

fun NavGraphBuilder.newsScreen(padding: PaddingValues) {
    composable(NavRoutes.NEWS) {
        NewsScreen(padding = padding)
    }
}
