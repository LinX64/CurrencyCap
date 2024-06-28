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
        ratingDto = RatingDto(
            weissDto = WeissDto(
                marketPerformanceRating = it.ratingDto.weissDto.marketPerformanceRating,
                rating = it.ratingDto.weissDto.rating,
                technologyAdoptionRating = it.ratingDto.weissDto.technologyAdoptionRating
            )
        ),
        type = it.type,
        url = it.url
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
