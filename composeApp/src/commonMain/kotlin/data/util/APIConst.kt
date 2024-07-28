package data.util

object APIConst {
    const val BASE_URL = "https://raw.githubusercontent.com/LinX64/CurrencyCapPortal/master/currencies.json"
    const val NEWS_URL = "https://raw.githubusercontent.com/LinX64/CurrencyCapPortal/master/news.json"
    const val CRYPTO_URL =
        "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false"
    const val CRYPTO_ICON_API = "https://linx64.github.io/cryptoicon-api/public/icons/"
    const val CRYPTO_INFO_URL = "https://api.coingecko.com/api/v3/coins"
    const val CRYPTO_MARKET_URL = "https://api.coingecko.com/api/v3/coins"
}