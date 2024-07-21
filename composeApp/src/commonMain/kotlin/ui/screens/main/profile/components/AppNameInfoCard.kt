package ui.screens.main.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import ui.components.base.button.SecondaryButton
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_32

@Composable
internal fun AppNameInfoCard(
    onSignOutClicked: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = SPACER_PADDING_16, vertical = SPACER_PADDING_32),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Currency Cap v1.0.0",
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Text(
            text = "© 2024 Currency Cap. All rights reserved.",
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_32))

        SecondaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Sign Out",
            onButtonClick = onSignOutClicked
        )
    }
}
