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
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.app_name
import currencycap.composeapp.generated.resources.app_version
import currencycap.composeapp.generated.resources.copy_right
import currencycap.composeapp.generated.resources.sign_out
import org.jetbrains.compose.resources.stringResource
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
            text = stringResource(Res.string.app_name) + " " + stringResource(Res.string.app_version),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(Res.string.copy_right),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_32))

        SecondaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.sign_out),
            onButtonClick = onSignOutClicked
        )
    }
}
