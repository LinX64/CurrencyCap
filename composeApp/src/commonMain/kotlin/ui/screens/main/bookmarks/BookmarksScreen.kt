package ui.screens.main.bookmarks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.bookmark
import currencycap.composeapp.generated.resources.explore_news
import currencycap.composeapp.generated.resources.no_bookmarks_description
import currencycap.composeapp.generated.resources.no_bookmarks_yet
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import org.jetbrains.compose.resources.stringResource
import ui.components.NewsItem
import ui.components.base.BaseGlassLazyColumn
import ui.components.base.button.SecondaryButton
import ui.screens.main.bookmarks.BookmarksState.NoBookmarks
import ui.screens.main.bookmarks.BookmarksState.Success
import ui.screens.main.bookmarks.BookmarksViewEvent.OnRemoveBookmarkClick
import ui.theme.AppDimensions.SPACER_PADDING_16
import ui.theme.AppDimensions.SPACER_PADDING_32

@Composable
internal fun BookmarksRoute(
    bookmarksViewModel: BookmarksViewModel = koinViewModel<BookmarksViewModel>(),
    hazeState: HazeState,
    onExploreNewsClick: () -> Unit,
    onBookmarkItemClick: (url: String) -> Unit
) {
    val state by bookmarksViewModel.viewState.collectAsStateWithLifecycle()

    BookmarksScreen(
        state = state,
        hazeState = hazeState,
        onExploreNewsClick = onExploreNewsClick,
        onBookmarkItemClick = onBookmarkItemClick,
        handleEvent = bookmarksViewModel::handleEvent
    )
}

@Composable
internal fun BookmarksScreen(
    hazeState: HazeState,
    state: BookmarksState,
    onExploreNewsClick: () -> Unit,
    onBookmarkItemClick: (url: String) -> Unit,
    handleEvent: (BookmarksViewEvent) -> Unit,
) {
    BaseGlassLazyColumn(
        hazeState = hazeState,
        isEmpty = state is NoBookmarks,
        emptyContent = { NoBookmarks(onExploreNewsClick = onExploreNewsClick) },
        content = {
            if (state is Success) {
                val articles = state.articles
                items(
                    count = articles.size,
                    key = { articles[it].url }
                ) { index ->
                    NewsItem(
                        article = articles[index],
                        shouldShowBookmark = true,
                        onNewsItemClick = {
                            val encodedUrl = UrlEncoderUtil.encode(articles[index].url)
                            onBookmarkItemClick(encodedUrl)
                        },
                        onBookmarkClick = { handleEvent(OnRemoveBookmarkClick(articles[index])) }
                    )
                }
            }
        }
    )
}

@Composable
private fun NoBookmarks(
    onExploreNewsClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(64.dp),
            imageVector = Icons.Default.Bookmarks,
            contentDescription = stringResource(Res.string.bookmark),
            tint = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.no_bookmarks_yet),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = SPACER_PADDING_32),
            text = stringResource(Res.string.no_bookmarks_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            minLines = 2
        )

        Spacer(modifier = Modifier.height(SPACER_PADDING_16))

        SecondaryButton(
            modifier = Modifier
                .padding(horizontal = SPACER_PADDING_32),
            text = stringResource(Res.string.explore_news),
            onButtonClick = onExploreNewsClick
        )
    }
}



