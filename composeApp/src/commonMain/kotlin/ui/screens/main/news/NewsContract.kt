package ui.screens.main.news

import domain.model.Article

sealed interface NewsViewEvent {
    data object FetchNews : NewsViewEvent
    data object OnRetry : NewsViewEvent

    data class OnBookmarkArticle(
        val article: Article,
        val isBookmarked: Boolean
    ) : NewsViewEvent
}

sealed interface NewsState {
    data object Idle : NewsState
    data object Loading : NewsState
    data class Success(val news: List<Article>) : NewsState

    data class Error(
        val message: String,
        val news: List<Article>
    ) : NewsState

    data object Empty : NewsState
}

sealed interface NewsNavigationEffect
