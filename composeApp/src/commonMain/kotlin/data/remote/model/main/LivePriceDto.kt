package data.remote.model.main

import kotlinx.serialization.Serializable

@Serializable
data class LivePriceDto(
    val id: String,
    val price: Double,
)