package ui.screens.main.news.news_detail

import domain.model.Article

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
    data class OpenBottomSheet(val url: String) : NewsDetailNavigationEffect
    data class ShowError(val message: String) : NewsDetailNavigationEffect
}

