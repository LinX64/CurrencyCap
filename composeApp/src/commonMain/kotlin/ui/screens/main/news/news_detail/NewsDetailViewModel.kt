package ui.screens.main.news.news_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import data.util.Constant.GET_ARTICLE_BY_URL_NEW
import domain.repository.NewsRepository
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import ui.common.MviViewModel
import ui.navigation.util.ENCODED_URL
import ui.screens.main.news.news_detail.NewsDetailNavigationEffect.OpenBottomSheet
import ui.screens.main.news.news_detail.NewsDetailNavigationEffect.ShowError
import ui.screens.main.news.news_detail.NewsDetailState.Loading
import ui.screens.main.news.news_detail.NewsDetailState.Success
import ui.screens.main.news.news_detail.NewsDetailViewEvent.FetchNews
import ui.screens.main.news.news_detail.NewsDetailViewEvent.OnReadMoreClick

class NewsDetailViewModel(
    private val newsRepository: NewsRepository,
    savedStateHandle: SavedStateHandle
) : MviViewModel<NewsDetailViewEvent, NewsDetailState, NewsDetailNavigationEffect>(Loading) {

    val url: String = savedStateHandle.get<String>(ENCODED_URL) ?: ""

    init {
        handleEvent(FetchNews(url))
    }

    override fun handleEvent(event: NewsDetailViewEvent) {
        when (event) {
            is FetchNews -> fetchArticleBy(event.url)
            is OnReadMoreClick -> setEffect(OpenBottomSheet(event.url))
        }
    }

    private fun fetchArticleBy(url: String) {
        viewModelScope.launch {
            val store = newsRepository.getArticleByUrlNew(url)
            store.stream(StoreReadRequest.cached(GET_ARTICLE_BY_URL_NEW, false))
                .collect { response ->
                    when (response) {
                        is StoreReadResponse.Loading -> setState { Loading }
                        is StoreReadResponse.Data -> {
                            val article = response.value
                            setState { Success(article) }
                        }

                        is StoreReadResponse.Error -> {
                            val error = response.errorMessageOrNull()
                            setEffect(
                                ShowError(error ?: "An error occurred while fetching the news")
                            )
                        }

                        else -> Unit
                    }
                }
        }
    }
}