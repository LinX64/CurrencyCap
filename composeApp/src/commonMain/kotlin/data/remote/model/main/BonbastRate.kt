package data.remote.model.main

import kotlinx.serialization.Serializable

@Serializable
data class BonbastRate(
    val code: String,
    val sell: Double,
    val buy: Double
)
