package ui.screens.main.news

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.viewModelScope
import com.mvicompose.linx64.ui.MviViewModel
import data.remote.model.news.toEntity
import data.util.NetworkResult
import domain.model.Article
import domain.repository.ArticleLocalDataSource
import domain.repository.NewsRepository
import domain.repository.UserPreferences
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ui.screens.main.news.NewsState.Empty
import ui.screens.main.news.NewsState.Error
import ui.screens.main.news.NewsState.Loading
import ui.screens.main.news.NewsState.Success
import ui.screens.main.news.NewsViewEvent.FetchNews
import ui.screens.main.news.NewsViewEvent.OnBookmarkArticle
import ui.screens.main.news.NewsViewEvent.OnRetry
import ui.screens.main.news.NewsViewEvent.OnSetClick
import util.convertToLocalDate

class NewsViewModel(
    private val newsRepository: NewsRepository,
    private val articleLocalDataSource: ArticleLocalDataSource,
    private val userPreferences: UserPreferences
) : MviViewModel<NewsViewEvent, NewsState, NewsNavigationEffect>(Loading), DefaultLifecycleObserver {

    val sources = mutableStateOf(persistentSetOf<String>())

    init {
        handleEvent(FetchNews)
    }

    override fun handleEvent(event: NewsViewEvent) {
        when (event) {
            FetchNews -> fetchNews()
            OnRetry -> fetchNews()
            is OnBookmarkArticle -> handleOnBookmarkClick(event.article, event.isBookmarked)
            is OnSetClick -> {
                saveSelectedDatesAndFilter(event.startDate, event.endDate)
                saveSelectedSources(event.selectedSources)
            }
        }
    }

    private fun fetchNews() {
        newsRepository.getNews()
            .map { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        setState { Success(result.data.sortedBy { it.publishedAt }.reversed()) }
                        getSources(result.data)
                    }

                    is NetworkResult.Error -> {
                        val news = result.data?.sortedBy { it.publishedAt }?.reversed() ?: emptyList()
                        val message = result.throwable.message ?: ""

                        getSources(news)

                        if (news.isNotEmpty()) setState { Success(news) }
                        else setState { Error(message) }
                    }

                    else -> setState { Loading }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun handleOnBookmarkClick(article: Article, isBookmarked: Boolean) {
        viewModelScope.launch {
            val updatedArticle = article.copy(isBookmarked = isBookmarked)
            articleLocalDataSource.updateArticle(updatedArticle.toEntity())
        }
    }

    private fun filterNewsBy(startDate: String, endDate: String) {
        val currentState = viewState.value
        if (currentState is Success) {
            val news = currentState.news
            val filteredNews = news.filter { article ->
                val articleDate = convertToLocalDate(article.publishedAt)
                val formattedStartDate = convertToLocalDate(startDate)
                val formattedEndDate = convertToLocalDate(endDate)

                articleDate in formattedStartDate..formattedEndDate
            }

            setState { if (filteredNews.isNotEmpty()) Success(filteredNews) else Empty }
        }
    }

    private fun saveSelectedDatesAndFilter(startDate: String, endDate: String) {
        viewModelScope.launch {
            userPreferences.saveUserSelectedDates(startDate, endDate)
        }

        filterNewsBy(startDate, endDate)
    }

    private fun getSources(news: List<Article>) {
        val sourcesNames = news
            .map { it.source.name }
            .distinct()

        sources.value = sourcesNames.toPersistentSet()
    }

    private fun saveSelectedSources(strings: Set<String>) {
        viewModelScope.launch {
            userPreferences.saveUserSelectedSources(strings)
        }

        filterNewsBySources(strings)
    }

    private fun filterNewsBySources(strings: Set<String>) {
        val currentState = viewState.value
        if (currentState is Success) {
            val news = currentState.news
            val filteredNews = news.filter { article ->
                article.source.name in strings
            }

            setState { if (filteredNews.isNotEmpty()) Success(filteredNews) else Empty }
        }
    }
}
