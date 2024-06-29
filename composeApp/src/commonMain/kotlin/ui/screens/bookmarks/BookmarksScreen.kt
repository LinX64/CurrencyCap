package ui.screens.bookmarks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import currencycap.composeapp.generated.resources.Res
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.components.NewsItem
import ui.components.main.BaseBlurLazyColumn
import ui.screens.bookmarks.BookmarksViewEvent.OnRemoveBookmarkClick

@Composable
internal fun BookmarksScreen(
    padding: PaddingValues,
    bookmarksViewModel: BookmarksViewModel = koinViewModel<BookmarksViewModel>(),
    hazeState: HazeState,
    onBookmarkItemClick: (url: String) -> Unit
) {
    val state by bookmarksViewModel.viewState.collectAsStateWithLifecycle()
    BaseBlurLazyColumn(
        hazeState = hazeState,
        padding = padding,
    ) {
        when (state) {
            BookmarksState.Idle -> Unit
            BookmarksState.NoBookmarks -> {
                item { NoBookmarks() }
            }

            is BookmarksState.Success -> {
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
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun NoBookmarks() {
    var bytes by remember { mutableStateOf(ByteArray(0)) }
    val composition by rememberLottieComposition(LottieCompositionSpec.JsonString(bytes.decodeToString()))

    LaunchedEffect(Unit) { bytes = Res.readBytes("files/no_bookmarks.json") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier.padding(16.dp).size(150.dp),
            composition = composition
        )

        Text(
            text = "No bookmarks yet!",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Bookmark a coin or a news to see it here",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}



