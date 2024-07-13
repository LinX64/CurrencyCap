package ui.navigation.util

import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ai_prediction
import currencycap.composeapp.generated.resources.bookmarks
import currencycap.composeapp.generated.resources.crypto_details
import currencycap.composeapp.generated.resources.exchange
import currencycap.composeapp.generated.resources.explore
import currencycap.composeapp.generated.resources.fill_profile
import currencycap.composeapp.generated.resources.forgot_password
import currencycap.composeapp.generated.resources.get_verified
import currencycap.composeapp.generated.resources.landing
import currencycap.composeapp.generated.resources.login
import currencycap.composeapp.generated.resources.news
import currencycap.composeapp.generated.resources.news_detail
import currencycap.composeapp.generated.resources.overview
import currencycap.composeapp.generated.resources.profile
import currencycap.composeapp.generated.resources.register
import currencycap.composeapp.generated.resources.settings
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource

@Serializable
sealed interface Screen {
    val route: StringResource

    @Serializable
    data object Overview : Screen {
        override val route: StringResource = Res.string.overview
    }

    @Serializable
    data class NewsDetail(val url: String) : Screen {
        override val route: StringResource = Res.string.news_detail
    }

    @Serializable
    data object AIPredict : Screen {
        override val route: StringResource = Res.string.ai_prediction
    }

    @Serializable
    data object Explore : Screen {
        override val route: StringResource = Res.string.explore
    }

    @Serializable
    data class CryptoDetail(val symbol: String) : Screen {
        override val route: StringResource = Res.string.crypto_details
    }

    @Serializable
    data object News : Screen {
        override val route: StringResource = Res.string.news
    }

    @Serializable
    data object Bookmarks : Screen {
        override val route: StringResource = Res.string.bookmarks
    }

    @Serializable
    data object Exchange : Screen {
        override val route: StringResource = Res.string.exchange
    }

    @Serializable
    data object Profile : Screen {
        override val route: StringResource = Res.string.profile
    }

    @Serializable
    data object Settings : Screen {
        override val route: StringResource = Res.string.settings
    }

    /**
     * Initial screens
     */

    @Serializable
    data object Landing : Screen {
        override val route: StringResource = Res.string.landing
    }

    @Serializable
    data object Login : Screen {
        override val route: StringResource = Res.string.login
    }

    @Serializable
    data object Register : Screen {
        override val route: StringResource = Res.string.register
    }

    @Serializable
    data object ResetPassword : Screen {
        override val route: StringResource = Res.string.forgot_password
    }

    @Serializable
    data object FillProfile : Screen {
        override val route: StringResource = Res.string.fill_profile
    }

    @Serializable
    data object GetVerifiedPhone : Screen {
        override val route: StringResource = Res.string.get_verified
    }
}