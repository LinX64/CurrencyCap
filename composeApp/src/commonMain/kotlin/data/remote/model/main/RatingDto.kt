package data.remote.model.main

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingDto(
    @SerialName("Weiss")
    val weissDto: WeissDto
)

@Serializable
data class WeissDto(
    @SerialName("MarketPerformanceRating")
    val marketPerformanceRating: String,
    @SerialName("Rating")
    val rating: String,
    @SerialName("TechnologyAdoptionRating")
    val technologyAdoptionRating: String
)