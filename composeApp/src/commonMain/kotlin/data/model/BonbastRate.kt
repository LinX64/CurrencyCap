package data.model

import domain.model.RateDao
import kotlinx.serialization.Serializable

@Serializable
data class BonbastRate(
    val code: String,
    val sell: Int,
    val buy: Int
)

fun List<BonbastRate>.toDomain(): List<RateDao> {
    return map { it.toDomain() }
}

fun BonbastRate.toDomain(): RateDao {
    return RateDao(code, sell, buy)
}