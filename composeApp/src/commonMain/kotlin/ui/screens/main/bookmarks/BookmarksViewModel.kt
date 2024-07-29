package ui.screens.main.bookmarks

import androidx.lifecycle.viewModelScope
import domain.model.Article
import domain.repository.ArticleLocalDataSource
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.common.MviViewModel
import ui.screens.main.bookmarks.BookmarksViewEvent.OnLoadBookmarks

class BookmarksViewModel(
    private val articleLocalDataSource: ArticleLocalDataSource
) : MviViewModel<BookmarksViewEvent, BookmarksState, BookmarksNavigationEffect>(BookmarksState.Idle) {

    init {
        handleEvent(OnLoadBookmarks)
    }

    override fun handleEvent(event: BookmarksViewEvent) {
        when (event) {
            OnLoadBookmarks -> onLoadBookmarks()
            is BookmarksViewEvent.OnRemoveBookmarkClick -> removeBookmark(event.article)
        }
    }

    private fun onLoadBookmarks() {
        articleLocalDataSource.getBookmarkedArticles()
            .map { articles ->
                when {
                    articles.isEmpty() -> setState { BookmarksState.NoBookmarks }
                    else -> setState { BookmarksState.Success(articles.toImmutableList()) }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun removeBookmark(article: Article) {
        viewModelScope.launch {
            articleLocalDataSource.removeArticle(article)
        }
    }
}