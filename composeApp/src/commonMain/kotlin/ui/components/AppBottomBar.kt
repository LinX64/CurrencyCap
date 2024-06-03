package ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild

@Composable
internal fun AppBottomBar(
    hazeState: HazeState,
    currentDestination: String,
    navController: NavHostController
) {
    NavigationBar(
        containerColor = Color.Transparent,
    ) {
        BottomAppBar(
            modifier = Modifier.fillMaxWidth()
                .hazeChild(state = hazeState),
            containerColor = Color.Transparent,
        ) {
            IconToggleButton(
                checked = currentDestination == "Home",
                onCheckedChange = {
                    if (it) {
                        navController.navigate("Home")
                    }
                },
                colors = IconToggleButtonColors(
                    contentColor = Color.White,
                    checkedContentColor = Color.White,
                    containerColor = Color.Transparent,
                    checkedContainerColor = Color.Transparent,
                    disabledContentColor = Color.White.copy(alpha = 0.5f),
                    disabledContainerColor = Color.Transparent,
                ),
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            }
        }
    }
}
