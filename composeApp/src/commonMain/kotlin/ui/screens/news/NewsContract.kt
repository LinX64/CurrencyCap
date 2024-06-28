package ui.screens.news

import data.remote.model.news.Article

sealed interface NewsViewEvent {
    data object FetchNews : NewsViewEvent
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

