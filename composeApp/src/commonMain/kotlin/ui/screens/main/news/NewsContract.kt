package ui.screens.main.news

import androidx.compose.runtime.Stable
import domain.model.Article

sealed interface NewsViewEvent {
    data class FetchNews(val forceRefresh: Boolean = false) : NewsViewEvent
    data object OnRetry : NewsViewEvent

    @Stable
    data class OnSetClick(
        val startDate: String,
        val endDate: String,
        val selectedSources: Set<String>
    ) : NewsViewEvent

    @Stable
    data class OnBookmarkArticle(
        val article: Article,
        val isBookmarked: Boolean
    ) : NewsViewEvent
}

sealed interface NewsState {
    data object Idle : NewsState
    data object Loading : NewsState

    @Stable
    data class Success(val news: List<Article>) : NewsState

    @Stable
    data class Error(val message: String) : NewsState
    data object Empty : NewsState
}

sealed interface NewsNavigationEffect {
    data class ShowBookmarkConfirmation(val isBookmarked: Boolean) : NewsNavigationEffect
}
