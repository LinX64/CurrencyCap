package ui.components.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ui.common.MviViewModel

@Composable
internal fun <Effect : Any> HandleNavigationEffect(
    viewModel: MviViewModel<*, *, Effect>,
    onEffect: (Effect) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            onEffect(effect)
        }
    }
}