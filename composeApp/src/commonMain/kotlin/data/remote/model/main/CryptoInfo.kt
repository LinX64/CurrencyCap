package data.remote.model.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CryptoInfo(
    @SerialName("description")
    val description: Description,
)

@Serializable
data class Description(
    @SerialName("en")
    val en: String
)