package ui.screens.main.news.news_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.model.Article
import ui.components.GlassCard
import ui.screens.main.overview.components.getPlaceHolder
import util.convertDateFormat

@Composable
internal fun NewsDetailContent(
    article: Article,
    isLoading: Boolean = false,
    imageUrl: String? = null,
    onReadMoreClick: (url: String) -> Unit
) {
    val loadingPlaceHolderModifier = if (isLoading) getPlaceHolder(Modifier.fillMaxWidth()) else Modifier.fillMaxWidth()
    val roundedCornerShape = RoundedCornerShape(35.dp)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = if (isLoading) getPlaceHolder(
                Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(roundedCornerShape)
            ) else Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(roundedCornerShape),
            model = imageUrl,
            contentDescription = null,
            clipToBounds = true,
            alpha = 0.5f,
            contentScale = ContentScale.FillBounds
        )

        GlassCard(
            modifier = Modifier.fillMaxWidth().offset(y = (-55).dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
            ) {
                val textModifierPlaceHolder =
                    if (isLoading) getPlaceHolder(Modifier.fillMaxWidth().padding(24.dp)) else Modifier.fillMaxWidth()
                        .padding(24.dp)
                Text(
                    modifier = textModifierPlaceHolder,
                    text = article.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = loadingPlaceHolderModifier,
                    text = article.source.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                val author = "By ${article.author}" + " • " + convertDateFormat(article.publishedAt)
                Text(
                    modifier = loadingPlaceHolderModifier,
                    text = author,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = loadingPlaceHolderModifier,
                    text = article.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    TextButton(
                        onClick = { onReadMoreClick(article.url) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Read More",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
