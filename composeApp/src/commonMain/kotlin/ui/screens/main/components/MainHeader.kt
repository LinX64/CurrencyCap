package ui.screens.main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import ui.theme.colors.CurrencyColors

@Composable
internal fun MainHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 50.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Market Overview",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FirstColumn()
            CircleCard()
        }

        Spacer(modifier = Modifier.height(32.dp))

        PerformanceChart(
            Modifier
                .fillMaxWidth()
                .height(60.dp),
            list = mockAssetInfo.lastDayChange
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0..4) {
                val buttonNames = listOf("H", "D", "W", "M", "All")
                CircleButton(
                    text = buttonNames[i],
                    onClick = {}
                )
            }
        }
    }
}

@Composable
private fun CircleButton(
    text: String,
    onClick: () -> Unit,
) {
    val isAll = text == "All"

    Card(
        modifier = Modifier
            .size(40.dp),
        onClick = onClick,
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isAll) CurrencyColors.LemonGreen.copy(alpha = 0.5f) else Color.Transparent,
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
private fun FirstColumn() {
    Column(
        modifier = Modifier.padding(top = 16.dp),
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Analytics",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Stock performance",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = "All time",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        )
    }
}

@Composable
private fun CircleCard(
    modifier: Modifier = Modifier,
) {
    val hazeState = remember { HazeState() }
    val isColorRed = data.valueChange < 0

    Box(
        modifier
            .padding(top = 16.dp)
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
                .hazeChild(
                    state = hazeState,
                    shape = RoundedCornerShape(16.dp),
                ),
        ) {
            Row(
                modifier = Modifier.padding(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ChangeIcon(data.valueChange)

                Text(
                    text = "-42.64%",
                    color = if (isColorRed) Color.Red else Color.Green,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
