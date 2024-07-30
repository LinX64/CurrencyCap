package data.remote.model.main

import data.local.model.main.CurrenciesEntity
import domain.model.main.Crypto
import domain.model.main.Market
import domain.model.main.Rate
import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesDto(
    @SerialName("bonbast")
    val bonbast: ImmutableList<BonbastRateDto>,
    @SerialName("crypto")
    val crypto: ImmutableList<CryptoDto>,
    @SerialName("markets")
    var market: ImmutableList<MarketDto>,
    @SerialName("rates")
    val rates: ImmutableList<RateDto>
) {
    fun toEntity() = CurrenciesEntity().apply {
        bonbast = this@CurrenciesDto.bonbast.toEntity()
        crypto = this@CurrenciesDto.crypto.toEntity()
        markets = this@CurrenciesDto.market.toEntity()
        rates = this@CurrenciesDto.rates.toEntity()
    }
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