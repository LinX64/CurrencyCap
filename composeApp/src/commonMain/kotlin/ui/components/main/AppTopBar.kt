package ui.components.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_arrow_left
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import org.jetbrains.compose.resources.painterResource
import ui.navigation.util.ScreenRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppTopBar(
    currentDestination: String?,
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    hazeState: HazeState
) {
    val isSettingsScreen = currentDestination == ScreenRoutes.SETTINGS
    val isNewsDetailScreen = currentDestination?.startsWith(ScreenRoutes.NEWS_DETAIL)
    val isDetailScreen = currentDestination?.startsWith(ScreenRoutes.CRYPTO_DETAIL)
    val isExploreScreen = currentDestination == ScreenRoutes.EXPLORE
    val isAiScreen = currentDestination == ScreenRoutes.AI_PREDICTION

    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth()
            .hazeChild(state = hazeState),
        title = {
            if (isNewsDetailScreen?.not() == true && isDetailScreen?.not() == true) {
                Text(
                    text = currentDestination,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
        ),
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (isSettingsScreen ||
                isNewsDetailScreen == true ||
                isExploreScreen ||
                isAiScreen ||
                isDetailScreen == true
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_left),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    )
}

