package ui.screens.news.news_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import data.model.news.Article
import data.model.news.Source
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import ui.components.BlurColumn
import ui.components.HandleNavigationEffect
import ui.components.main.BaseBlurLazyColumn
import ui.screens.overview.components.getPlaceHolder
import util.convertDateFormat

@Composable
internal fun NewsDetailScreen(
    padding: PaddingValues,
    newsDetailViewModel: NewsDetailViewModel = koinViewModel<NewsDetailViewModel>(),
    hazeState: HazeState,
    url: String
) {
    val state = newsDetailViewModel.viewState.collectAsStateWithLifecycle()

    println("NewsDetailScreen: $url")

    BaseBlurLazyColumn(
        hazeState = hazeState,
        padding = padding,
        contentPadding = PaddingValues(8.dp)
    ) {
        if (state.value is NewsDetailState.Success) {
            val article = (state.value as NewsDetailState.Success).article
            item {
                NewsDetailHeader(article = article)
            }
        }

        if (state.value is NewsDetailState.Loading) {
            item {
                NewsDetailHeader(article = getDummyNewsItem(), isLoading = true)
            }
        }
    }

    HandleNavigationEffect(newsDetailViewModel) { effect ->
        when (effect) {
            is NewsDetailNavigationEffect.NavigateToNewsDetailDetail -> {
                /*TODO*/
            }
        }
    }
}


internal fun getDummyNewsItem(): Article = Article(
    source = Source(
        id = "id",
        name = "CoinDesk"
    ),
    author = "Alvin Hemedez",
    title = "New Crypto Presale Pepe Unchained Goes Live – What Is PEPU Meme Token",
    description = "epe’s global popularity has led to the launch of Pepe Unchained, an enhanced version that has quickly trended as a… Continue reading New Crypto Presale Pepe Unchained Goes Live – What Is PEPU Meme Token\\nThe post New Crypto Presale Pepe Unchained Goes Live – …",
    url = "url",
    urlToImage = "https://readwrite.com/wp-content/uploads/2024/06/new-crypto-presale-pepe-unchained-goes-live.jpg",
    publishedAt = "2024-06-23T14:24:37Z",
    content = "Pepe’s global popularity has led to the launch of Pepe Unchained, an enhanced version that has quickly trended as a high-potential presale project that could be the best alternative to PEPE.\\r\\nThe pro… [+6203 chars]\""
)

@Composable
fun NewsDetailHeader(
    article: Article,
    isLoading: Boolean = false,
    imageUrl: String = article.urlToImage.toString()
) {
    val roundedCornerShape = RoundedCornerShape(35.dp)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = if (isLoading) getPlaceHolder(
                Modifier
                    .size(150.dp)
                    .fillMaxWidth()
            ) else Modifier
                .size(150.dp)
                .fillMaxWidth(),
            model = imageUrl,
            contentDescription = null,
            clipToBounds = true,
            alpha = 0.5f,
            contentScale = ContentScale.FillBounds
        )
        BlurColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(24.dp)
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = article.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = article.source.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            val author = "By ${article.author}" + " • " + convertDateFormat(article.publishedAt)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = author,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = article.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Justify
            )
        }
    }
}

