package ui.screens.ai_predict.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import ui.navigation.util.NavRoutes
import ui.screens.ai_predict.AiPredictScreen

fun NavController.navigateToAiPredictScreen() = navigate(NavRoutes.AI_PREDICTION)

fun NavGraphBuilder.aiPredictScreen(padding: PaddingValues, hazeState: HazeState) {
    composable(NavRoutes.AI_PREDICTION) {
        AiPredictScreen(padding = padding, hazeState = hazeState)
    }
}
