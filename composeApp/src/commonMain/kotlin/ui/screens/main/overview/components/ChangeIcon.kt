package ui.screens.main.overview.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.baseline_trending_down_24
import currencycap.composeapp.generated.resources.baseline_trending_up_24
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun ChangeIcon(
    valueChange: Double,
    isLoading: Boolean = false
) {
    val tint: Color
    val painter: Painter

    if (valueChange > 0) {
        painter = painterResource(Res.drawable.baseline_trending_up_24)
        tint = Color.Green
    } else {
        painter = painterResource(Res.drawable.baseline_trending_down_24)
        tint = Color.Red
    }

    Icon(
        modifier = if (isLoading) getPlaceHolder(Modifier.size(17.dp)) else Modifier,
        painter = painter,
        contentDescription = null,
        tint = tint
    )
}
