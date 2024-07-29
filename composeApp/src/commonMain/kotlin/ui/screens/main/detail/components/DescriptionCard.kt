package ui.screens.main.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign.Companion.Justify
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.description
import org.jetbrains.compose.resources.stringResource
import ui.screens.main.overview.components.getPlaceHolder
import ui.screens.main.profile.components.HelpCenterBaseGlassCard

@Composable
internal fun DescriptionCard(
    description: String,
    isLoading: Boolean = false
) {
    HelpCenterBaseGlassCard(
        title = stringResource(Res.string.description),
        badgeColor = MaterialTheme.colorScheme.secondary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp),
        ) {
            Text(
                modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
                text = description,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = Justify,
                maxLines = 24
            )
        }
    }
}