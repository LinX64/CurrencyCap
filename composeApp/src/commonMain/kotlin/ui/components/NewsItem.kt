package ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_bookmark_filled
import currencycap.composeapp.generated.resources.ic_bookmark_not_filled
import domain.model.Article
import org.jetbrains.compose.resources.painterResource
import ui.components.base.GlassCard
import ui.screens.main.overview.components.getPlaceHolder
import ui.theme.AppDimensions.CARD_CORNER_RADIUS
import ui.theme.AppDimensions.SPACER_PADDING_8
import ui.theme.colors.CurrencyColors
import util.convertDateFormat

@Composable
internal fun NewsItem(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    article: Article,
    shouldShowBookmark: Boolean = false,
    onNewsItemClick: (url: String) -> Unit,
    onBookmarkClick: (isBookmarked: Boolean) -> Unit
) {
    GlassCard(
        modifier = modifier.padding(vertical = SPACER_PADDING_8),
        isClickable = true,
        onCardClick = { onNewsItemClick(article.url) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = SPACER_PADDING_8, vertical = SPACER_PADDING_8),
        ) {
            FirstImageTextColumn(
                isLoading = isLoading,
                imageUrl = article.urlToImage,
                sourceName = article.source.name,
            )
            TextContentSection(
                title = article.title,
                description = article.description,
                date = article.publishedAt,
                isLoading = isLoading,
                shouldShowBookmark = shouldShowBookmark,
                onBookmarkClick = onBookmarkClick
            )
        }
    }
}

@Composable
private fun FirstImageTextColumn(
    isLoading: Boolean,
    imageUrl: String? = null,
    sourceName: String
) {
    val roundedCornerShape = RoundedCornerShape(CARD_CORNER_RADIUS)

    Column(
        modifier = Modifier.wrapContentHeight(),
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
            contentDescription = null,
            contentScale = ContentScale.Crop
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
    date: String,
    shouldShowBookmark: Boolean = false,
    onBookmarkClick: (isBookmarked: Boolean) -> Unit
) {
    var isBookmarked by rememberSaveable { mutableStateOf(shouldShowBookmark) }
    val bookmarkIconColor = if (isBookmarked) CurrencyColors.Yellow.primary else MaterialTheme.colorScheme.onSurface
    val bookmarkIcon = if (isBookmarked) Res.drawable.ic_bookmark_filled else Res.drawable.ic_bookmark_not_filled

    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = SPACER_PADDING_8
        ),
    ) {
        IconButton(
            onClick = {
                isBookmarked = !isBookmarked
                onBookmarkClick(isBookmarked)
            },
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.End)
        ) {
            Icon(
                painter = painterResource(bookmarkIcon),
                contentDescription = null,
                tint = bookmarkIconColor
            )
        }

        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_8))

        Text(
            modifier = if (isLoading) getPlaceHolder(Modifier) else Modifier,
            text = convertDateFormat(date),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_8))

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