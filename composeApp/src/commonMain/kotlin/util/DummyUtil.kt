package util

import data.remote.model.User
import domain.model.Article
import domain.model.News
import domain.model.Source
import domain.model.main.BonbastRate
import domain.model.main.Crypto
import domain.model.main.Currencies
import domain.model.main.Market
import domain.model.main.Rate

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

internal fun getDummyUser() = User(
    email = "email",
    fullName = "fullName",
    phoneNumber = "phoneNumber",
    profilePicture = ""
)

internal fun getDummyBonbastRates(): List<BonbastRate> = listOf(
    BonbastRate(
        code = "code", sell = 8.9, buy = 10.11,
    )
)

internal fun getDummyMarketRates(): List<Market> = listOf(
    Market(
        baseId = "te",
        baseSymbol = "brute",
        exchangeId = "labores",
        percentExchangeVolume = "sociis",
        priceQuote = "invidunt",
        priceUsd = "nostrum",
        quoteId = "id",
        quoteSymbol = "per",
        rank = "iudicabit",
        tradesCount24Hr = null,
        updated = 3698,
        volumeUsd24Hr = "adipisci"
    )
)

internal fun getDummyRatesItem(): List<Rate> = listOf(
    Rate(
        id = "vero", rateUsd = "ultricies", symbol = "animal", type = "proin", currencySymbol = null
    )
)

internal fun getDummyNews(): List<News> = listOf(
    News(
        articles = listOf(
            getDummyNewsItem()
        ),
        status = "status",
        totalResults = 1
    )
)

internal fun getDummyCurrencies(): Currencies = Currencies(
    bonbast = getDummyBonbastRates(),
    crypto = getDummyCryptoItems(),
    markets = getDummyMarketRates(),
    rates = getDummyRatesItem()
)

