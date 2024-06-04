package ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppTopBar(
    name: String = "Currency Cap",
    navController: NavHostController,
    currentDestination: NavDestination?,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val hazeState = remember { HazeState() }
    CenterAlignedTopAppBar(
        modifier = Modifier
            .hazeChild(
                state = hazeState,
                style = HazeDefaults.style(backgroundColor = Color.Transparent)
            ),
        title = { AppTitle(name = name) },
        navigationIcon = {
//            if (currentDestination?.route != NavRoutes.Home.ROUTE) {
//                AppNavigationIcon(navController = navController)
//            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            titleContentColor = Color.Transparent,
            navigationIconContentColor = Color.Transparent,
            actionIconContentColor = Color.Transparent,
        )
    )
}

@Composable
private fun AppTitle(name: String) {
    Text(
        text = name,
        maxLines = 1,
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
    )
}

@Composable
fun AppNavigationIcon(navController: NavHostController) {
    IconButton(
        onClick = { navController.navigateUp() }
    ) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, tint = Color.White, contentDescription = null)
    }
}


