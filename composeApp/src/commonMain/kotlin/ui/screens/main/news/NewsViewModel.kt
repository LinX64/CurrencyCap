package ui.screens.main.news

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import data.remote.model.news.toEntity
import data.util.Constant.NEWS_KEY
import domain.model.Article
import domain.repository.ArticleLocalDataSource
import domain.repository.NewsRepository
import domain.repository.UserPreferences
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import ui.common.MviViewModel
import ui.screens.main.news.NewsState.Empty
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
) : MviViewModel<NewsViewEvent, NewsState, NewsNavigationEffect>(Loading) {

    val sources = mutableStateOf(emptyList<String>())

    init {
        handleEvent(FetchNews)
    }

    override fun handleEvent(event: NewsViewEvent) {
        when (event) {
            OnRetry -> fetchNewsNew()
            is FetchNews -> fetchNewsNew()
            is OnBookmarkArticle -> handleOnBookmarkClick(event.article, event.isBookmarked)
            is OnSetClick -> {
                saveSelectedDatesAndFilter(event.startDate, event.endDate)
                saveSelectedSources(event.selectedSources)
            }
        }
    }

    private fun fetchNewsNew() {
        viewModelScope.launch {
            val store = newsRepository.getNewsNew()
            store.stream(StoreReadRequest.freshWithFallBackToSourceOfTruth(NEWS_KEY))
                .collectLatest { response ->
                    when (response) {
                        is StoreReadResponse.Loading -> setState { Loading }
                        is StoreReadResponse.Data -> {
                            val news = response.value

                            if (news.isNotEmpty()) {
                                setState { Success(news) }
                                getSources(news)
                            } else setState { Empty }
                        }

                        is StoreReadResponse.Error -> setState { Empty }
                        else -> Unit
                    }
                }
        }
    }

    private fun handleOnBookmarkClick(article: Article, isBookmarked: Boolean) {
        viewModelScope.launch {
            val updatedArticle = article.copy(isBookmarked = isBookmarked)
            articleLocalDataSource.updateArticle(updatedArticle.toEntity())
        }

        setEffect(NewsNavigationEffect.ShowBookmarkConfirmation(isBookmarked))
    }

    private fun filterNewsBy(startDate: String?, endDate: String?) {
        val currentState = viewState.value
        if (currentState is Success) {
            val news = currentState.news
            val currentDate =
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

            val formattedStartDate = startDate?.let { convertToLocalDate(it) } ?: currentDate
            val formattedEndDate = endDate?.let { convertToLocalDate(it) } ?: currentDate

            val filteredNews = news.filter { article ->
                val articleDate = convertToLocalDate(article.publishedAt)
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
            .filter { it.source.name.isNotBlank() }
            .map { it.source.name }
            .distinct()
            .sorted()

        sources.value = sourcesNames
    }

    private fun saveSelectedSources(strings: Set<String>) {
        if (strings.isEmpty()) return

        viewModelScope.launch {
            userPreferences.saveUserSelectedSources(emptySet())
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

//    fun refresh() {
//        setState { Loading }
//        _isRefreshing.value = true
//
//        viewModelScope.launch {
//            delay(2500L)
//            _isRefreshing.value = false
//
//            handleEvent(FetchNews(true))
//        }
//    }// TODO fix this later
}
