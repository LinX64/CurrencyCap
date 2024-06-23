package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BonbastRateDto(
    val code: String,
    val sell: Int,
    val buy: Int
)
