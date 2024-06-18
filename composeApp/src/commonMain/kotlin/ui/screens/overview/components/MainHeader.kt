package ui.screens.overview.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ui.components.BlurColumn
import ui.screens.overview.OverviewState
import ui.theme.colors.CurrencyColors

@Composable
internal fun MainHeader(
    state: OverviewState
) {
    val isLoading = state is OverviewState.Loading
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FirstColumn()
            CircleCard(isLoading = isLoading)
        }

        Spacer(modifier = Modifier.height(32.dp))

        PerformanceChart(
            modifier = if (isLoading) getPlaceHolder(Modifier.height(60.dp)) else Modifier,
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
            fontWeight = FontWeight.Bold
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
    isLoading: Boolean
) {
    val isColorRed = data.valueChange < 0

    BlurColumn {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChangeIcon(valueChange = data.valueChange, isLoading = isLoading)

            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = "-42.64%",
                color = if (isColorRed) Color.Red else Color.Green,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
