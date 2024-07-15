package ui.screens.main.subscribers.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.components.base.ClickableCard
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun FreePlanCard(
    modifier: Modifier = Modifier,
    isFreeCardSelected: Boolean,
    onFreePlanClick: () -> Unit
) {
    val features = listOf(
        "Basic real-time market data",
        "Basic news and alerts"
    )
    ClickableCard(onClick = onFreePlanClick, isCardSelected = isFreeCardSelected) {
        Column(
            modifier = modifier
                .padding(horizontal = CARD_CORNER_RADIUS, vertical = CARD_CORNER_RADIUS),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Free Plan",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Your current plan",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(SPACER_PADDING_8))

            for (feature in features) {
                FeatureItem(feature)
            }
        }
    }
}
