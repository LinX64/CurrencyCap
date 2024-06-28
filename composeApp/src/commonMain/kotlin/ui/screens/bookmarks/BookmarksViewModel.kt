package ui.screens.bookmarks

import ui.common.MviViewModel

class BookmarksViewModel(
    private val bookmarksRepository: BookmarksRepository
) : MviViewModel<BookmarksViewEvent, BookmarksState, BookmarksNavigationEffect>(BookmarksState.Idle) {

    override fun handleEvent(event: BookmarksViewEvent) {
        TODO("Not yet implemented")
    }
}