package ui.screens.main.ai_predict.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import ui.screens.main.ai_predict.AiPredictScreen

fun NavController.navigateToAiPredictScreen() = navigate(AIPrediction)

fun NavGraphBuilder.aiPredictScreen(
    padding: PaddingValues,
    hazeState: HazeState
) {
    composable<AIPrediction> {
        AiPredictScreen(
            padding = padding,
            hazeState = hazeState
        )
    }
}

@Serializable
data object AIPrediction