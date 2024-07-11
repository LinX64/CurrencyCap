package ui.screens.main.ai_predict.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import ui.navigation.util.Screen.AIPredict
import ui.screens.main.ai_predict.AiPredictScreen

fun NavController.navigateToAiPredictScreen() = navigate(AIPredict)

fun NavGraphBuilder.aiPredictScreen(
    padding: PaddingValues,
    hazeState: HazeState
) {
    composable<AIPredict> {
        AiPredictScreen(
            padding = padding,
            hazeState = hazeState
        )
    }
}
