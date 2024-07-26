package ui.components.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import ui.screens.main.overview.components.getPlaceHolder
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun SectionRowItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    hasSubTitle: Boolean = false,
    title: String,
    subTitle: String? = null,
    hasEndText: Boolean = false,
    endText: String? = null,
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
                    modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
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
                Text(
                    text = endText ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}