package data.model

import domain.model.RateDao
import kotlinx.serialization.Serializable

@Serializable
data class Rate(
    val code: String,
    val sell: Int,
    val buy: Int
)

fun List<Rate>.toDomain(): List<RateDao> {
    return map { it.toDomain() }
}

fun Rate.toDomain(): RateDao {
    return RateDao(code, sell, buy)
}