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
import ui.navigation.util.Screens.AIPredict
import ui.navigation.util.Screens.CryptoDetail
import ui.navigation.util.Screens.Explore
import ui.navigation.util.Screens.NewsDetail
import ui.navigation.util.Screens.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppTopBar(
    currentDestination: String?,
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    hazeState: HazeState
) {
    val isSettingsScreen = currentDestination == Settings.route
    val isNewsDetailScreen = currentDestination?.startsWith(NewsDetail("").route)
    val isDetailScreen = currentDestination?.startsWith(CryptoDetail("").route)
    val isExploreScreen = currentDestination == Explore.route
    val isAiScreen = currentDestination == AIPredict.route

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

