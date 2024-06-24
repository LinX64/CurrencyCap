package domain.model

data class CurrenciesDto(
    val bonbast: List<BonbastRateDto>,
    val crypto: List<CryptoDto>,
    val markets: List<MarketDto>,
    val rates: List<RateDto>
)