package ui.screens.main.exchange.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun Header(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(CARD_CORNER_RADIUS)
    ) {
        Text(
            text = "from",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_8))
    }
}