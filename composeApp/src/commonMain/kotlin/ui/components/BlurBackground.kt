package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.colors.CurrencyColors

@Composable
internal fun BlurBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            CurrencyColors.LemonGreen.copy(alpha = 0.5f),
                            CurrencyColors.DarkLemonGreen.copy(alpha = 0.3f),
                            Color.Transparent
                        ),
                        startY = 0f,
                        endY = 1000f
                    )
                )
                .blur(16.dp)
        )
        content()
    }
}