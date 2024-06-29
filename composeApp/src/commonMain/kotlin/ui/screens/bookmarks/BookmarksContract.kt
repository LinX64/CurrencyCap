package ui.screens.bookmarks

import domain.model.Article

sealed interface BookmarksViewEvent {
    data object OnLoadBookmarks : BookmarksViewEvent
    data class OnRemoveBookmarkClick(val article: Article) : BookmarksViewEvent
}

sealed interface BookmarksState {
    data object Idle : BookmarksState
    data class Success(val articles: List<Article>) : BookmarksState
    data object NoBookmarks : BookmarksState
}

sealed interface BookmarksNavigationEffect {
    data class ShowSnakeBar(val message: String) : BookmarksNavigationEffect
}