package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.eygraber.compose.placeholder.PlaceholderHighlight
import com.eygraber.compose.placeholder.fade
import com.eygraber.compose.placeholder.placeholder

@Composable
internal fun ItemPlaceHolder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.clip(CircleShape)
            .placeholder(
                visible = true,
                highlight = PlaceholderHighlight.fade(
                    highlightColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                ),
                color = MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.1f
                )
            )
    )
}

val getPlaceHolder: @Composable Modifier.() -> Modifier = {
    placeholder(
        visible = true,
        highlight = PlaceholderHighlight.fade(
            highlightColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        ),
        color = MaterialTheme.colorScheme.primary.copy(
            alpha = 0.1f
        )
    )
}