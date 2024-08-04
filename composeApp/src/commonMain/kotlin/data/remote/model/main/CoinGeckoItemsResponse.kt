package data.remote.model.main

import kotlinx.serialization.Serializable

@Serializable
data class CoinGeckoItemsResponse(
    val id: String,
    val symbol: String,
    val name: String
)