package ui.screens.ai_predict

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExchangeRoute(
    aiPredictViewModel: AiPredictViewModel
) {
    ExchangeScreen()
}

@Composable
fun ExchangeScreen(
    modifier: Modifier = Modifier,
) {
//    val isMatchingEnabled = remember { mutableStateOf(false) }
//    val composition by rememberLottieComposition(
//        LottieCompositionSpec.RawRes(R.raw.ai_animation)
//    )
//    val progress by animateLottieCompositionAsState(
//        composition = composition,
//        iterations = if (isMatchingEnabled.value) LottieConstants.IterateForever else 1
//    )
//
//    Column(
//        modifier = modifier.fillMaxSize()
//    ) {
//        Column(
//            modifier = modifier
//                .fillMaxSize()
//                .padding(horizontal = 16.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            LottieAnimation(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .size(if (isMatchingEnabled.value) 300.dp else 250.dp),
//                composition = composition,
//                progress = { progress }
//            )
//
//            AnimatedContent(
//                targetState = isMatchingEnabled.value.not(),
//                transitionSpec = {
//                    fadeIn(
//                        animationSpec = tween(durationMillis = 50)
//                    ) togetherWith fadeOut(animationSpec = tween(durationMillis = 50))
//                },
//                label = ""
//            ) {
//                if (it) VisibleComponents()
//            }
//
//            AnimatedVisibility(
//                visible = isMatchingEnabled.value,
//                enter = fadeIn(initialAlpha = 0f),
//                exit = fadeOut(animationSpec = tween(durationMillis = 50))
//            ) {
//                HiddenComponents()
//            }
//        }
//    }
//
//    BottomButton(
//        isMatchingEnabled = isMatchingEnabled,
//        onStartMatchingClick = {
//            isMatchingEnabled.value = true
//            // TODO: Start matching
//        },
//        onCancelClick = {
//            isMatchingEnabled.value = false
//        }
//    )
}

