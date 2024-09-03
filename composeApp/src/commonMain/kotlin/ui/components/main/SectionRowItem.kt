package ui.components.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun SectionRowItem(
    modifier: Modifier = Modifier,
    hasSubTitle: Boolean = false,
    title: String,
    subTitle: String? = null,
    hasEndText: Boolean = false,
    endText: String? = null,
    onEndTextClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(horizontal = SPACER_PADDING_8),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (hasSubTitle) {
                    Text(
                        text = subTitle ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            if (hasEndText) {
                TextButton(
                    onClick = onEndTextClick,
                    content = {
                        Text(
                            text = endText ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )
            }
        }
    }
}