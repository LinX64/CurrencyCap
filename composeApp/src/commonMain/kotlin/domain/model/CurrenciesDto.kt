package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesDto(
    val bonbast: List<BonbastRateDto>,
    val crypto: List<CryptoDto>,
    val markets: List<MarketDto>,
    val rates: List<RateDto>
)