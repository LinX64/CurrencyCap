package data.remote.model.main

import domain.model.main.BonbastRate
import domain.model.main.Crypto
import domain.model.main.Market
import domain.model.main.Rate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesDto(
    @SerialName("bonbast")
    val bonbast: List<BonbastRateDto>,
    @SerialName("crypto")
    val crypto: List<CryptoDto>,
    @SerialName("markets")
    val market: List<MarketDto>,
    @SerialName("rates")
    val rates: List<RateDto>
)

internal fun List<BonbastRateDto>.toBonbastRateDomain(): List<BonbastRate> = map {
    BonbastRate(
        code = it.code,
        sell = it.sell,
        buy = it.buy
    )
}

internal fun List<CryptoDto>.toCryptoDomain(): List<Crypto> = map {
    Crypto(
        ath = it.ath,
        athChangePercentage = it.athChangePercentage,
        athDate = it.athDate,
        atl = it.atl,
        atlChangePercentage = it.atlChangePercentage,
        atlDate = it.atlDate,
        circulatingSupply = it.circulatingSupply,
        currentPrice = it.currentPrice,
        fullyDilutedValuation = it.fullyDilutedValuation,
        high24h = it.high24h,
        id = it.id,
        image = it.image,
        lastUpdated = it.lastUpdated,
        low24h = it.low24h,
        marketCap = it.marketCap,
        marketCapChange24h = it.marketCapChange24h,
        marketCapChangePercentage24h = it.marketCapChangePercentage24h,
        marketCapRank = it.marketCapRank,
        maxSupply = it.maxSupply,
        name = it.name,
        priceChange24h = it.priceChange24h,
        priceChangePercentage24h = it.priceChangePercentage24h,
        symbol = it.symbol,
        totalSupply = it.totalSupply,
        totalVolume = it.totalVolume
    )
}

internal fun List<MarketDto>.toMarketDomain(): List<Market> = map {
    Market(
        baseId = it.baseId,
        baseSymbol = it.baseSymbol,
        exchangeId = it.exchangeId,
        percentExchangeVolume = it.percentExchangeVolume,
        priceQuote = it.priceQuote,
        priceUsd = it.priceUsd,
        quoteId = it.quoteId,
        quoteSymbol = it.quoteSymbol,
        rank = it.rank,
        tradesCount24Hr = it.tradesCount24Hr,
        updated = it.updated,
        volumeUsd24Hr = it.volumeUsd24Hr
    )
}

internal fun List<RateDto>.toRateDomain(): List<Rate> = map {
    Rate(
        id = it.id,
        rateUsd = it.rateUsd,
        symbol = it.symbol,
        type = it.type,
        currencySymbol = it.currencySymbol
    )
}
