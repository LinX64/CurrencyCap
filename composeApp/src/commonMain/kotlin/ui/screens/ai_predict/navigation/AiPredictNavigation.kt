package ui.screens.ai_predict.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ui.components.BlurBackground
import ui.navigation.NavRoutes
import ui.screens.ai_predict.AiPredictScreen

fun NavController.navigateToAiPredictScreen(navOptions: NavOptions) = navigate(NavRoutes.AI_PREDICTION, navOptions)

fun NavGraphBuilder.aiPredictScreen(padding: PaddingValues) {
    composable(NavRoutes.AI_PREDICTION) {
        BlurBackground {
            AiPredictScreen(padding = padding)
        }
    }
}
