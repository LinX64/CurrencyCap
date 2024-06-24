package com.client.currencycap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun CardGradient(modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .aspectRatio(600 / 400f)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Gradient()
    }
}

@Composable
fun Gradient() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Layout(content = {
            Box(modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawRect(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xff3e91f0), Color.Transparent
                            ),
                            center = Offset(this.size.width * 0.05f, this.size.height / 2),
                            radius = size.width * 2,
                        )
                    )
                })
            Box(modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawRect(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xffFF8A58), Color.Transparent
                            ),
                            center = Offset(0f, this.size.height),
                            radius = size.height,
                        )
                    )
                })
            Box(modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawRect(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xff58EBFF), Color.Transparent
                            ),
                            center = Offset(
                                this.size.width * 1.1f, this.size.height * .100f
                            ),
                            radius = size.height,
                        )
                    )
                })
            Box(modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawRect(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xff8c54eb), Color.Transparent
                            ),
                            center = Offset(
                                this.size.width, this.size.height * 0.9f
                            ),
                            radius = size.height,
                        )
                    )
                })

        }, measurePolicy = { measurables, constraints ->
            val placeables = measurables.map { it.measure(constraints) }
            layout(constraints.maxWidth, constraints.maxHeight) {
                placeables.forEach { it.place(0, 0) }
            }
        })
    }
}