package ui.screens.bookmarks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import ui.components.NewsItem
import ui.components.main.BaseGlassLazyColumn
import ui.screens.bookmarks.BookmarksViewEvent.OnRemoveBookmarkClick

@Composable
internal fun BookmarksScreen(
    padding: PaddingValues,
    bookmarksViewModel: BookmarksViewModel = koinViewModel<BookmarksViewModel>(),
    hazeState: HazeState,
    onBookmarkItemClick: (url: String) -> Unit
) {
    val state by bookmarksViewModel.viewState.collectAsStateWithLifecycle()
    BaseGlassLazyColumn(
        hazeState = hazeState,
        padding = padding,
        isEmpty = state is BookmarksState.NoBookmarks,
        emptyContent = { NoBookmarks() },
        content = {
            if (state is BookmarksState.Success) {
                val articles = (state as BookmarksState.Success).articles
                items(articles.size) { index ->
                    NewsItem(
                        article = articles[index],
                        shouldShowBookmark = true,
                        onNewsItemClick = {
                            val encodedUrl = UrlEncoderUtil.encode(articles[index].url)
                            onBookmarkItemClick(encodedUrl)
                        },
                        onBookmarkClick = { bookmarksViewModel.handleEvent(OnRemoveBookmarkClick(articles[index])) }
                    )
                }
            }
        }
    )
}

@Composable
private fun NoBookmarks() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(64.dp),
            imageVector = Icons.Default.Bookmarks,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "No bookmarks yet!",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Bookmark a news to see it here",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}



