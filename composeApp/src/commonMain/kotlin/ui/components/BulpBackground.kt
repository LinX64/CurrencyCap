package ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun BulbBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val darkBlue = Color(0xFF000000)
    val lightBlue = Color(0xFF012342)
    val lightBlueCircle = Color(0xFF003C70)

    Box(
        modifier = Modifier.fillMaxSize().then(modifier)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val height = size.height
            val width = size.width

            // Draw the vertical gradient background
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(darkBlue, lightBlue),
                    startY = 0f,
                    endY = height
                )
            )

            // Draw the top right diffused light effect using Compose Brush
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(lightBlueCircle.copy(alpha = 0.6f), Color.Transparent),
                    center = Offset(width - 200f, 200f),
                    radius = 600f
                ),
                radius = 600f,
                center = Offset(width - 200f, 200f)
            )

            // Draw the left-center diffused light effect using Compose Brush
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(lightBlueCircle.copy(alpha = 0.6f), Color.Transparent),
                    center = Offset(200f, height / 2),
                    radius = 300f
                ),
                radius = 300f,
                center = Offset(200f, height / 2)
            )
        }

        content()
    }
}