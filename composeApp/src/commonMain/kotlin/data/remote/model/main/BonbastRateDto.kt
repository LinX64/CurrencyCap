package data.remote.model.main

import data.local.model.main.BonbastRateEntity
import io.realm.kotlin.ext.toRealmList
import kotlinx.serialization.Serializable

@Serializable
data class BonbastRateDto(
    val code: String,
    val sell: Double,
    val buy: Double
) {
    fun toEntity() = BonbastRateEntity().apply {
        code = this@BonbastRateDto.code
        sell = this@BonbastRateDto.sell
        buy = this@BonbastRateDto.buy
    }
}

internal fun List<BonbastRateDto>.toEntity() = map { it.toEntity() }.toRealmList()