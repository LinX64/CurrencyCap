package ui.navigation

import kotlinx.serialization.Serializable

sealed class Screens {

    @Serializable
    data object Overview : Screens()

    @Serializable
    data class NewsDetail(val symbol: String) : Screens()

    @Serializable
    data object AIPredict : Screens()

    @Serializable
    data object Explore : Screens()

    @Serializable
    data class CryptoDetail(val symbol: String) : Screens()

    @Serializable
    data object News : Screens()

    @Serializable
    data object Bookmarks : Screens()

    @Serializable
    data object Exchange : Screens()

    @Serializable
    data class Profile(val url: String) : Screens()

    @Serializable
    data object Settings : Screens()
}