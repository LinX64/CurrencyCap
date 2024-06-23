package data.model

import domain.model.BonbastRateDto
import domain.model.CryptoDto
import domain.model.CurrenciesDto
import domain.model.MarketDto
import domain.model.RateDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Currencies(
    @SerialName("bonbast")
    val bonbast: List<BonbastRate>,
    @SerialName("crypto")
    val crypto: List<Crypto>,
    @SerialName("markets")
    val markets: List<Market>,
    @SerialName("rates")
    val rates: List<Rate>
)

internal fun Currencies.toDomain(): CurrenciesDto = CurrenciesDto(
    bonbast = bonbast.toDomain(),
    crypto = crypto.toDomain(),
    markets = markets.toDomain(),
    rates = rates.toDomain()
)

internal fun List<BonbastRate>.toDomain(): List<BonbastRateDto> = map {
    BonbastRateDto(
        code = it.code,
        sell = it.sell,
        buy = it.buy
    )
}

internal fun List<Crypto>.toDomain(): List<CryptoDto> = map {
    CryptoDto(
        algorithm = it.algorithm,
        assetLaunchDate = it.assetLaunchDate,
        blockNumber = it.blockNumber,
        blockReward = it.blockReward,
        blockTime = it.blockTime,
        documentType = it.documentType,
        fullName = it.fullName,
        id = it.id,
        imageUrl = it.imageUrl,
        internal = it.internal,
        maxSupply = it.maxSupply,
        name = it.name,
        netHashesPerSecond = it.netHashesPerSecond,
        proofType = it.proofType,
        rating = Rating(
            weiss = Weiss(
                marketPerformanceRating = it.rating.weiss.marketPerformanceRating,
                rating = it.rating.weiss.rating,
                technologyAdoptionRating = it.rating.weiss.technologyAdoptionRating
            )
        ),
        type = it.type,
        url = it.url
    )
}

internal fun List<Market>.toDomain(): List<MarketDto> = map {
    MarketDto(
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

internal fun List<Rate>.toDomain(): List<RateDto> = map {
    RateDto(
        id = it.id,
        rateUsd = it.rateUsd,
        symbol = it.symbol,
        type = it.type,
        currencySymbol = it.currencySymbol
    )
}