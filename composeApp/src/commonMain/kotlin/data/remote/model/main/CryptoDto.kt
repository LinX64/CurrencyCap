package data.remote.model.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoDto(
    @SerialName("Algorithm")
    val algorithm: String,
    @SerialName("AssetLaunchDate")
    val assetLaunchDate: String,
    @SerialName("BlockNumber")
    val blockNumber: Int,
    @SerialName("BlockReward")
    val blockReward: Double,
    @SerialName("BlockTime")
    val blockTime: Double,
    @SerialName("DocumentType")
    val documentType: String,
    @SerialName("FullName")
    val fullName: String,
    @SerialName("Id")
    val id: String,
    @SerialName("ImageUrl")
    val imageUrl: String,
    @SerialName("Internal")
    val `internal`: String,
    @SerialName("MaxSupply")
    val maxSupply: Double,
    @SerialName("Name")
    val name: String,
    @SerialName("NetHashesPerSecond")
    val netHashesPerSecond: Double,
    @SerialName("ProofType")
    val proofType: String,
    @SerialName("Rating")
    val ratingDto: RatingDto,
    @SerialName("Type")
    val type: Int,
    @SerialName("Url")
    val url: String
)