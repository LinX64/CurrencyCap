package ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import ui.navigation.util.ScreenRoutes

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun NotLoggedInTopAppBar(
    currentDestination: String?,
    navController: NavHostController,
    hazeState: HazeState,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth()
            .hazeChild(state = hazeState),
        title = { },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        navigationIcon = { NavigationIcon(currentDestination, navController) },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun NavigationIcon(
    currentDestination: String?,
    navController: NavHostController
) {
    currentDestination ?: return

    if (isNavigationIconVisible(currentDestination)) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }
    }
}

private fun isNavigationIconVisible(currentDestination: String) = (currentDestination != ScreenRoutes.LANDING)
        && (currentDestination != ScreenRoutes.LOGIN)
        && (currentDestination != ScreenRoutes.REGISTER)