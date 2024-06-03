package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesDao(
    val rates: List<RateDao>
)

@Serializable
data class RateDao(
    val code: String,
    val sell: Int,
    val buy: Int
)