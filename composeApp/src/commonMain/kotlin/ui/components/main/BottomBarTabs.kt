package ui.components.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BatchPrediction
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

    data object Wallet : BottomBarTab(
        title = "Wallet",
        icon = Icons.Default.AccountBalanceWallet,
    )

    data object Exchange : BottomBarTab(
        title = "Change",
    )

    data object AiPrediction : BottomBarTab(
        title = "Trade",
        icon = Icons.Default.BatchPrediction,
    )

    data object Profile : BottomBarTab(
        title = "Profile",
        icon = Icons.Default.Person,
    )
}

val tabs = listOf(
    BottomBarTab.Overview,
    BottomBarTab.Wallet,
    BottomBarTab.Exchange,
    BottomBarTab.AiPrediction,
    BottomBarTab.Profile
)

