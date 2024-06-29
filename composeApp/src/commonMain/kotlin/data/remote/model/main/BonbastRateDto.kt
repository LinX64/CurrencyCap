package data.remote.model.main

import kotlinx.serialization.Serializable

@Serializable
data class BonbastRateDto(
    val code: String,
    val sell: Double,
    val buy: Double
)
