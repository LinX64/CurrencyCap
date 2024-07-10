package ui.navigation.util

import kotlinx.serialization.Serializable
import ui.navigation.util.NavRoutes.AI_PREDICTION
import ui.navigation.util.NavRoutes.BOOKMARKS
import ui.navigation.util.NavRoutes.CRYPTO_DETAIL
import ui.navigation.util.NavRoutes.EXCHANGE
import ui.navigation.util.NavRoutes.EXPLORE
import ui.navigation.util.NavRoutes.FILL_PROFILE
import ui.navigation.util.NavRoutes.GET_VERIFIED_PHONE
import ui.navigation.util.NavRoutes.LANDING
import ui.navigation.util.NavRoutes.LOGIN
import ui.navigation.util.NavRoutes.NEWS
import ui.navigation.util.NavRoutes.NEWS_DETAIL
import ui.navigation.util.NavRoutes.OVERVIEW
import ui.navigation.util.NavRoutes.PROFILE
import ui.navigation.util.NavRoutes.REGISTER
import ui.navigation.util.NavRoutes.RESET_PASSWORD
import ui.navigation.util.NavRoutes.SETTINGS

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