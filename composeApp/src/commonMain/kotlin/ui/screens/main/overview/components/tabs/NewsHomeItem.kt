package ui.screens.main.overview.components.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.model.Article

@Composable
internal fun NewsHomeItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    newsItem: Article
) {
    Card(
        onClick = onClick
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
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

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = newsItem.author ?: "Unknown",
                    style = MaterialTheme.typography.bodyMedium,
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