import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import ui.screens.main.crypto_list.CryptoListRoute
import ui.screens.main.detail.navigation.CryptoDetail

fun NavGraphBuilder.cryptoListScreen(
    navController: NavController,
    hazeState: HazeState,
) {
    composable<CryptoList> {
        CryptoListRoute(
            hazeState = hazeState,
            onCryptoItemClick = { id, symbol -> navController.navigate(CryptoDetail(id, symbol)) }
        )
    }
}

@Serializable
data object CryptoList