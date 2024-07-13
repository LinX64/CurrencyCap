package ui.navigation.util

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object Overview

    @Serializable
    data class NewsDetail(val url: String)

    @Serializable
    data object AIPredict

    @Serializable
    data object Explore

    @Serializable
    data class CryptoDetail(val symbol: String)

    @Serializable
    data object News

    @Serializable
    data object Bookmarks

    @Serializable
    data object Exchange

    @Serializable
    data object Profile

    @Serializable
    data object Settings

    /**
     * Initial screens
     */

    @Serializable
    data object Landing

    @Serializable
    data object Login

    @Serializable
    data object Register

    @Serializable
    data object ResetPassword

    @Serializable
    data object FillProfile

    @Serializable
    data object GetVerifiedPhone
}