package ui.navigation.util

import kotlinx.serialization.Serializable
import ui.navigation.util.ScreenRoutes.AI_PREDICTION
import ui.navigation.util.ScreenRoutes.BOOKMARKS
import ui.navigation.util.ScreenRoutes.CRYPTO_DETAIL
import ui.navigation.util.ScreenRoutes.EXCHANGE
import ui.navigation.util.ScreenRoutes.EXPLORE
import ui.navigation.util.ScreenRoutes.FILL_PROFILE
import ui.navigation.util.ScreenRoutes.GET_VERIFIED_PHONE
import ui.navigation.util.ScreenRoutes.LANDING
import ui.navigation.util.ScreenRoutes.LOGIN
import ui.navigation.util.ScreenRoutes.NEWS
import ui.navigation.util.ScreenRoutes.NEWS_DETAIL
import ui.navigation.util.ScreenRoutes.OVERVIEW
import ui.navigation.util.ScreenRoutes.PROFILE
import ui.navigation.util.ScreenRoutes.REGISTER
import ui.navigation.util.ScreenRoutes.RESET_PASSWORD
import ui.navigation.util.ScreenRoutes.SETTINGS

@Serializable
sealed class Screens(val route: String) {

    @Serializable
    data object Overview : Screens(OVERVIEW)

    @Serializable
    data class NewsDetail(
        val url: String
    ) : Screens(NEWS_DETAIL)

    @Serializable
    data object AIPredict : Screens(AI_PREDICTION)

    @Serializable
    data object Explore : Screens(EXPLORE)

    @Serializable
    data class CryptoDetail(
        val symbol: String
    ) : Screens(CRYPTO_DETAIL)

    @Serializable
    data object News : Screens(NEWS)

    @Serializable
    data object Bookmarks : Screens(BOOKMARKS)

    @Serializable
    data object Exchange : Screens(EXCHANGE)

    @Serializable
    data object Profile : Screens(PROFILE)

    @Serializable
    data object Settings : Screens(SETTINGS)

    /**
     * Initial screens
     */

    @Serializable
    data object Landing : Screens(LANDING)

    @Serializable
    data object Login : Screens(LOGIN)

    @Serializable
    data object Register : Screens(REGISTER)

    @Serializable
    data object ResetPassword : Screens(RESET_PASSWORD)

    @Serializable
    data object FillProfile : Screens(FILL_PROFILE)

    @Serializable
    data object GetVerifiedPhone : Screens(GET_VERIFIED_PHONE)
}