package ui.screens.news.news_detail

import domain.model.Article

sealed interface NewsDetailViewEvent {
    data class FetchNews(val url: String) : NewsDetailViewEvent
    data object OnReadMoreClick : NewsDetailViewEvent
}

sealed interface NewsDetailState {
    data object Idle : NewsDetailState
    data object Loading : NewsDetailState
    data class Success(val article: Article) : NewsDetailState
    data class Error(val message: String) : NewsDetailState
}

sealed interface NewsDetailNavigationEffect {
    data class OpenBrowser(val url: String) : NewsDetailNavigationEffect
}

