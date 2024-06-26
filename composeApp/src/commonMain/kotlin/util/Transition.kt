package util

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith

fun enterTransition() = scaleIn(tween(durationMillis = 400)) + fadeIn(tween(durationMillis = 800))
fun exitTransition() = scaleOut(tween(durationMillis = 400)) + fadeOut(tween(durationMillis = 800))
fun transitionSpec() = enterTransition() togetherWith exitTransition()