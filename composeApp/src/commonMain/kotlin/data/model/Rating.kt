package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    @SerialName("Weiss")
    val weiss: Weiss
)

@Serializable
data class Weiss(
    @SerialName("MarketPerformanceRating")
    val marketPerformanceRating: String,
    @SerialName("Rating")
    val rating: String,
    @SerialName("TechnologyAdoptionRating")
    val technologyAdoptionRating: String
)