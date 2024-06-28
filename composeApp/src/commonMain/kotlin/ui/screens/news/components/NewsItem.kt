package ui.screens.news.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import data.model.news.Article
import ui.components.GlassCard
import ui.screens.overview.components.getPlaceHolder

@Composable
internal fun NewsItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    article: Article,
    onNewsItemClick: (url: String) -> Unit,
) {
    GlassCard(
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        Card(
            onClick = { onNewsItemClick(article.url) },
            shape = RoundedCornerShape(35.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
            ) {
                FirstImageTextColumn(
                    isLoading = isLoading,
                    imageUrl = article.urlToImage,
                    sourceName = article.source.name,
                )
                TextContentSection(
                    title = article.title,
                    description = article.description,
                    isLoading = isLoading,
                )
            }
        }
    }
}

@Composable
private fun FirstImageTextColumn(
    isLoading: Boolean,
    imageUrl: String? = null,
    sourceName: String
) {
    val roundedCornerShape = RoundedCornerShape(35.dp)

    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        AsyncImage(
            modifier = if (isLoading) getPlaceHolder(
                Modifier
                    .size(64.dp)
                    .clip(roundedCornerShape)
            ) else Modifier
                .size(64.dp)
                .clip(roundedCornerShape),
            model = imageUrl,
            onError = {
                // TODO: Handle error
            },
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = if (isLoading) getPlaceHolder(
                Modifier.width(64.dp)
            ) else Modifier.width(64.dp),
            text = sourceName,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TextContentSection(
    title: String,
    description: String,
    isLoading: Boolean,
    date: String = "2 Apr",
) {
    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
    ) {
        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = date,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = description,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        )
    }
}