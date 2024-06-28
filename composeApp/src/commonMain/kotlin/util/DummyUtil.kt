package util

import data.model.news.Article
import data.model.news.Source

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
