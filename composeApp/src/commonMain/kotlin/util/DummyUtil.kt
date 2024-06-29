package util

import domain.model.Article
import domain.model.Source

internal fun getDummyNewsItem() = Article(
    author = "Alvin Hemedez",
    content = "Pepe’s global popularity has led to the launch of Pepe Unchained, an enhanced version that has quickly trended as a high-potential presale project that could be the best alternative to PEPE.\\r\\nThe pro… [+6203 chars]\"",
    description = "epe’s global popularity has led to the launch of Pepe Unchained, an enhanced version that has quickly trended as a… Continue reading New Crypto Presale Pepe Unchained Goes Live – What Is PEPU Meme Token\\nThe post New Crypto Presale Pepe Unchained Goes Live – …",
    publishedAt = "2024-06-23T14:24:37Z",
    sourceDto = Source(
        id = "id",
        name = "CoinDesk"
    ),
    title = "New Crypto Presale Pepe Unchained Goes Live – What Is PEPU Meme Token",
    url = "url",
    urlToImage = "https://readwrite.com/wp-content/uploads/2024/06/new-crypto-presale-pepe-unchained-goes-live.jpg",
)
