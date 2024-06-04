package ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
internal fun AppBottomBar(
    modifier: Modifier = Modifier,
    currentDestination: String,
    navController: NavHostController,
    selectedIndex: Int,
    onItemClicked: (Int) -> Unit,
) {
    NavigationBar(
        containerColor = Color.Transparent,
        modifier = modifier,
    ) {
        for (i in (0 until 3)) {
            NavigationBarItem(
                selected = selectedIndex == i,
                onClick = { onItemClicked(i) },
                icon = {
                    Icon(
                        imageVector = when (i) {
                            0 -> Icons.Default.Call
                            1 -> Icons.Default.Lock
                            else -> Icons.Default.Search
                        },
                        contentDescription = null,
                    )
                },
            )
        }
    }
}
