package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crypto(
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
    @SerialName("ProofType")
    val proofType: String,
    @SerialName("Rating")
    val rating: Rating,
    @SerialName("Type")
    val type: Int,
    @SerialName("Url")
    val url: String
)