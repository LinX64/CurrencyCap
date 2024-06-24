package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
internal fun BlurBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF060E20)) // Dark background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawRect(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF9C27B0), Color.Transparent // Purple color
                            ),
                            center = Offset(this.size.width * 0.3f, this.size.height * 0.3f),
                            radius = size.width * 0.7f,
                        )
                    )
                })

        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawRect(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF4CAF50), Color.Transparent // Green color
                            ),
                            center = Offset(
                                this.size.width * 1.2f, this.size.height * .100f
                            ),
                            radius = size.height * 0.6f,
                        )
                    )
                })

        content()
    }
}
