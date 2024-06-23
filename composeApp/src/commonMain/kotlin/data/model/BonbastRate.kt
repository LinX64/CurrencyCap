package data.model

import kotlinx.serialization.Serializable

@Serializable
data class BonbastRate(
    val code: String,
    val sell: Int,
    val buy: Int
)
