package domain.model

import data.model.main.Rating

data class CryptoDto(
    val algorithm: String,
    val assetLaunchDate: String,
    val blockNumber: Int,
    val blockReward: Double,
    val blockTime: Double,
    val documentType: String,
    val fullName: String,
    val id: String,
    val imageUrl: String,
    val internal: String,
    val maxSupply: Double,
    val name: String,
    val netHashesPerSecond: Double,
    val proofType: String,
    val rating: Rating,
    val type: Int,
    val url: String
)