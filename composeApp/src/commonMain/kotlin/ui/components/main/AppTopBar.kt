package ui.components.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_logout
import currencycap.composeapp.generated.resources.ic_settings
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import org.jetbrains.compose.resources.painterResource
import ui.navigation.NavRoutes
import ui.screens.settings.navigation.navigateToSettingsScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
internal fun AppTopBar(
    currentDestination: String,
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    onLogoutClick: () -> Unit,
    hazeState: HazeState,
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.fillMaxWidth()
            .hazeChild(
                state = hazeState,
                style = HazeMaterials.regular(MaterialTheme.colorScheme.surface),
            ),
        title = { AppTitle(currentDestination = currentDestination) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
        ),
        scrollBehavior = scrollBehavior,
        actions = {
            ActionsMenu(
                currentDestination = currentDestination,
                onLogoutClick = onLogoutClick,
                navController = navController
            )
        }
    )
}

@Composable
private fun ActionsMenu(
    currentDestination: String,
    onLogoutClick: () -> Unit,
    navController: NavHostController
) {
    val expanded = remember { mutableStateOf(false) }

    if (currentDestination == NavRoutes.PROFILE) {
        IconButton(
            onClick = { navController.navigateToSettingsScreen() }
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_settings),
                contentDescription = "Settings"
            )
        }

        IconButton(
            onClick = { expanded.value = !expanded.value }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    onLogoutClick()
                    expanded.value = false
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_logout),
                        contentDescription = "Log out"
                    )
                },
                text = { Text("Log out") }
            )
        }
    }
}

@Composable
private fun AppTitle(currentDestination: String) {
    Text(
        text = currentDestination,
        maxLines = 1,
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        fontWeight = FontWeight.Bold
    )
}



