package ui.screens.main.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import ui.common.getTrendingDownIcon
import ui.common.getTrendingUpIcon

@Composable
internal fun ChangeIcon(valueChange: Int = -18) {
    val tint: Color
    val painter: Painter

    if (valueChange > 0) {
        painter = getTrendingUpIcon()
        tint = Color.Green
    } else {
        painter = getTrendingDownIcon()
        tint = Color.Red
    }

    Icon(
        modifier = Modifier.size(17.dp),
        painter = painter,
        contentDescription = null,
        tint = tint
    )
}
