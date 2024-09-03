import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import ui.screens.main.crypto_list.navigation.CryptoListRoute

fun NavGraphBuilder.cryptoListScreen(
    hazeState: HazeState,
) {
    composable<CryptoList> {
        CryptoListRoute(
            hazeState = hazeState,
        )
    }
}

@Serializable
data object CryptoList