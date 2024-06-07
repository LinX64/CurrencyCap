package ui.screens.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import domain.model.RateDao
import ui.common.getPlaceHolderDrawable
import util.formatToPrice

@Composable
fun RateItem(
    modifier: Modifier = Modifier,
    icon: String,
    rate: RateDao
) {
    val hazeState = remember { HazeState() }
        Box(
            modifier.fillMaxSize()
                .haze(
                    state = hazeState,
                    style = HazeDefaults.style(
                        tint = Color.White.copy(alpha = 0.1f),
                        blurRadius = 1.dp
                    ),
                ),
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .hazeChild(
                        state = hazeState,
                        shape = RoundedCornerShape(16.dp),
                    ),
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = 32.dp,
                        end = 32.dp,
                        top = 16.dp,
                        bottom = 16.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier.size(48.dp),
                        placeholder = getPlaceHolderDrawable(),
                        error = getPlaceHolderDrawable(),
                        model = icon,
                        contentDescription = null
                    )

                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = rate.code,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    val formattedPrice = rate.sell.formatToPrice()
                    Text(
                        text = "$formattedPrice t",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
}