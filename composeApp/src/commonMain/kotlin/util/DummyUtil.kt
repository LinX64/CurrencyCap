package util

import domain.model.Article
import domain.model.Source
import domain.model.main.Crypto

internal fun getDummyNewsItem() = Article(
    author = "Alvin Hemedez",
    content = "Pepe’s global popularity has led to the launch of Pepe Unchained, an enhanced version that has quickly trended as a high-potential presale project that could be the best alternative to PEPE.\\r\\nThe pro… [+6203 chars]\"",
    description = "epe’s global popularity has led to the launch of Pepe Unchained, an enhanced version that has quickly trended as a… Continue reading New Crypto Presale Pepe Unchained Goes Live – What Is PEPU Meme Token\\nThe post New Crypto Presale Pepe Unchained Goes Live – …",
    publishedAt = "2024-06-23T14:24:37Z",
    source = Source(
        id = "id",
        name = "CoinDesk"
    ),
    title = "New Crypto Presale Pepe Unchained Goes Live – What Is PEPU Meme Token",
    url = "url",
    urlToImage = "https://readwrite.com/wp-content/uploads/2024/06/new-crypto-presale-pepe-unchained-goes-live.jpg",
)

internal fun getDummyCryptoItem(): Crypto = Crypto(
    ath = 76.77,
    athChangePercentage = 78.79,
    athDate = "ornare",
    atl = 80.81,
    atlChangePercentage = 82.83,
    atlDate = "sententiae",
    circulatingSupply = 84.85,
    currentPrice = 86.87,
    fullyDilutedValuation = null,
    high24h = 88.89,
    id = "accusata",
    image = "similique",
    lastUpdated = "suspendisse",
    low24h = 90.91,
    marketCap = 2861,
    marketCapChange24h = 92.93,
    marketCapChangePercentage24h = 94.95,
    marketCapRank = 1879,
    maxSupply = null,
    name = "Becky Foreman",
    priceChange24h = 96.97,
    priceChangePercentage24h = 98.99,
    symbol = "sententiae",
    totalSupply = null,
    totalVolume = 100.101
)

internal fun getDummyCryptoItems() = listOf(
    Crypto(
        ath = 76.77,
        athChangePercentage = 78.79,
        athDate = "ornare",
        atl = 80.81,
        atlChangePercentage = 82.83,
        atlDate = "sententiae",
        circulatingSupply = 84.85,
        currentPrice = 86.87,
        fullyDilutedValuation = null,
        high24h = 88.89,
        id = "accusata",
        image = "similique",
        lastUpdated = "suspendisse",
        low24h = 90.91,
        marketCap = 2861,
        marketCapChange24h = 92.93,
        marketCapChangePercentage24h = 94.95,
        marketCapRank = 1879,
        maxSupply = null,
        name = "Becky Foreman",
        priceChange24h = 96.97,
        priceChangePercentage24h = 98.99,
        symbol = "sententiae",
        totalSupply = null,
        totalVolume = 100.101
    ),
    Crypto(
        ath = 76.77,
        athChangePercentage = 78.79,
        athDate = "ornare",
        atl = 80.81,
        atlChangePercentage = 82.83,
        atlDate = "sententiae",
        circulatingSupply = 84.85,
        currentPrice = 86.87,
        fullyDilutedValuation = null,
        high24h = 88.89,
        id = "accusata",
        image = "similique",
        lastUpdated = "suspendisse",
        low24h = 90.91,
        marketCap = 2861,
        marketCapChange24h = 92.93,
        marketCapChangePercentage24h = 94.95,
        marketCapRank = 1879,
        maxSupply = null,
        name = "Becky Foreman",
        priceChange24h = 96.97,
        priceChangePercentage24h = 98.99,
        symbol = "sententiae",
        totalSupply = null,
        totalVolume = 100.101
    ),
)