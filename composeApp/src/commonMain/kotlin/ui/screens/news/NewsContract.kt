package ui.screens.news

import domain.model.Article

sealed interface NewsViewEvent {
    data object FetchNews : NewsViewEvent
    data class OnBookmarkArticle(val article: Article) : NewsViewEvent
}

sealed interface NewsState {
    data object Idle : NewsState
    data object Loading : NewsState
    data class Success(val news: List<Article>) : NewsState
    data class Error(val message: String) : NewsState
}

sealed interface NewsNavigationEffect {
    data object NavigateToNewsDetail : NewsNavigationEffect
}

