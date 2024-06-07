package ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.navigation.components.NavigationIcon

@Composable
internal fun AppBottomBar(
    modifier: Modifier = Modifier,
    currentDestination: String,
    selectedIndex: Int,
    onItemClicked: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .clip(
                RoundedCornerShape(
                    topStart = 50.dp,
                    topEnd = 50.dp,
                    bottomStart = 50.dp,
                    bottomEnd = 50.dp
                )
            )
            .background(Color.DarkGray)
    ) {
        NavigationBar(
            modifier = modifier.fillMaxWidth()
                .navigationBarsPadding()
                .clip(
                    RoundedCornerShape(
                        topStart = 50.dp,
                        topEnd = 50.dp,
                        bottomStart = 50.dp,
                        bottomEnd = 50.dp
                    )
                ),
            containerColor = Color.Transparent
        ) {
            NavigationItem(
                selectedIndex = selectedIndex,
                onItemClicked = onItemClicked
            )
        }
    }
}

@Composable
internal fun RowScope.NavigationItem(
    selectedIndex: Int,
    onItemClicked: (Int) -> Unit
) {
    for (i in 0 until 3) {
        NavigationBarItem(
            selected = selectedIndex == i,
            onClick = { onItemClicked(i) },
            alwaysShowLabel = true,
            icon = { NavigationIcon(i) },
            colors = NavigationBarItemColors(
                selectedIconColor = Color.Transparent,
                unselectedIconColor = Color.Transparent,
                selectedTextColor = Color.Transparent,
                unselectedTextColor = Color.Transparent,
                selectedIndicatorColor = Color.Transparent,
                disabledIconColor = Color.Transparent,
                disabledTextColor = Color.Transparent,
            ),
        )
    }
}