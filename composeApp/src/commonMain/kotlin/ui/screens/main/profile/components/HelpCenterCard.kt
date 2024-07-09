package ui.screens.main.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun HelpCenterCard(
    onButtonClick: () -> Unit
) {
    HelpCenterBaseGlassCard(
        title = "Help Center",
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            HelpCenterItem(text = "Support", onButtonClick = onButtonClick)
        }
    }
}
