package util

import data.remote.model.User
import domain.model.Article
import domain.model.Source
import domain.model.main.Crypto
import domain.model.main.CryptoImage
import domain.model.main.CryptoInfo
import domain.model.main.Description
import domain.model.main.MarketData
import kotlinx.collections.immutable.persistentListOf

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
    isBookmarked = false
)

internal fun getDummyCryptoItem(): Crypto = Crypto(
    ath = 0.0,
    athChangePercentage = 0.0,
    athDate = "",
    atl = 0.0,
    atlChangePercentage = 0.0,
    atlDate = "",
    circulatingSupply = 0.0,
    currentPrice = 0.0,
    fullyDilutedValuation = null,
    high24h = 0.0,
    id = "",
    image = "",
    lastUpdated = "",
    low24h = 0.0,
    marketCap = 0,
    marketCapChange24h = 0.0,
    marketCapChangePercentage24h = 0.0,
    marketCapRank = 0,
    maxSupply = null,
    name = "",
    priceChange24h = 0.0,
    priceChangePercentage24h = 0.0,
    symbol = "",
    totalSupply = null,
    totalVolume = 0.0
)

internal fun getDummyCryptoInfoItem(): CryptoInfo = CryptoInfo(
    id = "id",
    description = Description(en = "melius"),
    marketData = MarketData(
        currentPrice = 50.51,
        high24h = 52.53,
        low24h = 54.55,
        priceChange24h = 56.57,
        priceChangePercentage24h = 58.59,
        priceChangePercentage7d = 60.61,
        priceChangePercentage14d = 62.63,
        priceChangePercentage30d = 64.65,
        priceChangePercentage60d = 66.67,
        priceChangePercentage200d = 68.69,
        priceChangePercentage1y = 70.71,
        ath = 72.73,
        marketCap = 74.75,
    ),
    name = "Bitcoin",
    symbol = "btc",
    image = CryptoImage(
        large = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
        small = "https://assets.coingecko.com/coins/images/1/small/bitcoin.png?1547033579",
        thumb = "https://assets.coingecko.com/coins/images/1/thumb/bitcoin.png?1547033579"
    )
)

internal fun getDummyCryptoItems() = persistentListOf(
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
        name = "Bitcoin",
        priceChange24h = 96.97,
        priceChangePercentage24h = 98.99,
        symbol = "btc",
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
        name = "Ethereum",
        priceChange24h = 96.97,
        priceChangePercentage24h = 98.99,
        symbol = "eth",
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
        name = "Ethereum",
        priceChange24h = 96.97,
        priceChangePercentage24h = 98.99,
        symbol = "eth",
        totalSupply = null,
        totalVolume = 100.101
    ),
)

internal fun getDummyUser() = User(
    email = "email",
    fullName = "fullName",
    phoneNumber = "phoneNumber",
    profilePicture = ""
)