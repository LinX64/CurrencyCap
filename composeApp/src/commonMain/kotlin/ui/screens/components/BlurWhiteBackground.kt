package ui.screens.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
internal fun BlurLightBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val pastelPink = Color(0xFFFFC1E3)
    val pastelBlue = Color(0xFFA7C7E7)
    val lightYellow = Color(0xFFFFF59D)

    Box(
        modifier = Modifier.fillMaxSize().then(modifier)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val height = size.height
            val width = size.width

            // Draw the vertical gradient background
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(pastelPink, pastelBlue),
                    startY = 0f,
                    endY = height
                )
            )

            // Draw the top right diffused light effect using Compose Brush
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(lightYellow.copy(alpha = 0.6f), Color.Transparent),
                    center = Offset(width - 200f, 200f),
                    radius = 600f
                ),
                radius = 600f,
                center = Offset(width - 200f, 200f)
            )

            // Draw the left-center diffused light effect using Compose Brush
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(lightYellow.copy(alpha = 0.6f), Color.Transparent),
                    center = Offset(200f, height / 2),
                    radius = 300f
                ),
                radius = 300f,
                center = Offset(200f, height / 2)
            )

            // Draw the bottom right diffused light effect using Compose Brush
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(lightYellow.copy(alpha = 0.6f), Color.Transparent),
                    center = Offset(width - 200f, height - 200f),
                    radius = 600f
                ),
                radius = 600f,
                center = Offset(width - 200f, height - 200f)
            )
        }

        content()
    }
}