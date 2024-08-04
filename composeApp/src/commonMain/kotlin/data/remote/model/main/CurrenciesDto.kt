package data.remote.model.main

import data.local.model.main.CurrenciesEntity
import domain.model.main.Crypto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesDto(
    @SerialName("bonbast")
    val bonbast: List<BonbastRateDto>,
    @SerialName("crypto")
    val crypto: List<CryptoDto>,
    @SerialName("markets")
    var market: List<MarketDto>,
    @SerialName("rates")
    val rates: List<RateDto>
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