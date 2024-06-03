package ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController

@Composable
internal fun AppTopBar(
    isFirstLogin: Boolean,
    name: String,
    notificationCount: Int,
    navController: NavHostController,
    currentDestination: NavDestination?
) {

}

