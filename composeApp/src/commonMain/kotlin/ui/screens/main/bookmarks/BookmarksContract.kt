package ui.screens.main.bookmarks

import domain.model.Article
import kotlinx.collections.immutable.ImmutableList

sealed interface BookmarksViewEvent {
    data object OnLoadBookmarks : BookmarksViewEvent
    data class OnRemoveBookmarkClick(val article: Article) : BookmarksViewEvent
}

sealed interface BookmarksState {
    data object Idle : BookmarksState
    data class Success(val articles: ImmutableList<Article>) : BookmarksState
    data object NoBookmarks : BookmarksState
}

sealed interface BookmarksNavigationEffect