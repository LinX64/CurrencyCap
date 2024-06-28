package ui.screens.bookmarks

sealed interface BookmarksViewEvent {
}

sealed interface BookmarksState {
    data object Idle : BookmarksState
}

sealed interface BookmarksNavigationEffect {
    data class ShowSnakeBar(val message: String) : BookmarksNavigationEffect
}