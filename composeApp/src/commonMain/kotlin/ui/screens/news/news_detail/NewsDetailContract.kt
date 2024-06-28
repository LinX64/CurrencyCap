package ui.screens.news.news_detail

import data.model.news.Article

sealed interface NewsDetailViewEvent {
    data class FetchNews(val url: String) : NewsDetailViewEvent
    data class OnReadMoreClick(val url: String) : NewsDetailViewEvent
}

sealed interface NewsDetailState {
    data object Idle : NewsDetailState
    data object Loading : NewsDetailState
    data class Success(val article: Article) : NewsDetailState
    data class Error(val message: String) : NewsDetailState
}

sealed interface NewsDetailNavigationEffect {
    data object NavigateToNewsDetailDetail : NewsDetailNavigationEffect
}

