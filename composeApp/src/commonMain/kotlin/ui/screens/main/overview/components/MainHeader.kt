package ui.screens.main.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ui.components.GlassCard
import ui.screens.main.overview.OverviewState

@Composable
internal fun MainHeader(
    state: OverviewState
) {
    val isLoading = state is OverviewState.Loading
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
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
            modifier = if (isLoading) getPlaceHolder(Modifier.height(100.dp)) else Modifier,
            list = mockAssetInfo.lastDayChange
        )

        Spacer(modifier = Modifier.height(32.dp))

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

    GlassCard {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            ChangeIcon(valueChange = data.valueChange, isLoading = isLoading)

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
