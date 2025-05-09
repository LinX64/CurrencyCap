package ui.screens.main.overview.components.tabs.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.model.Article
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun NewsHomeItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    newsItem: Article
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = newsItem.urlToImage,
                    contentDescription = "News Image",
                    modifier = Modifier.size(40.dp)
                )

                // TODO: Replace with image for news

                Spacer(modifier = Modifier.width(SPACER_PADDING_8))

                Text(
                    text = newsItem.author ?: "Unknown",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = newsItem.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )
        }
    }
}