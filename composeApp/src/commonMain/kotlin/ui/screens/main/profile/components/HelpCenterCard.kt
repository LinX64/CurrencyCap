package ui.screens.main.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.help_center
import currencycap.composeapp.generated.resources.support
import org.jetbrains.compose.resources.stringResource
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun HelpCenterCard(
    onButtonClick: () -> Unit
) {
    HelpCenterBaseGlassCard(
        title = stringResource(Res.string.help_center),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(SPACER_PADDING_8))
            HelpCenterItem(text = stringResource(Res.string.support), onButtonClick = onButtonClick)
        }
    }
}
