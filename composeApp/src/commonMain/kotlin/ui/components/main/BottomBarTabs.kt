package ui.components.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatchPrediction
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import ui.navigation.NavRoutes

sealed class BottomBarTab(
    val title: String,
    val icon: ImageVector? = null,
) {
    data object Overview : BottomBarTab(
        title = NavRoutes.HOME,
        icon = Icons.Rounded.Home,
    )

    data object News : BottomBarTab(
        title = NavRoutes.NEWS,
        icon = Icons.Default.Newspaper
    )

    data object Exchange : BottomBarTab(
        title = NavRoutes.EXCHANGE,
    )

    data object AiPrediction : BottomBarTab(
        title = NavRoutes.AI_PREDICTION,
        icon = Icons.Default.BatchPrediction,
    )

    data object Profile : BottomBarTab(
        title = "Profile",
        icon = Icons.Default.Person,
    )
}

val tabs = listOf(
    BottomBarTab.Overview,
    BottomBarTab.News,
    BottomBarTab.Exchange,
    BottomBarTab.AiPrediction,
    BottomBarTab.Profile
)

