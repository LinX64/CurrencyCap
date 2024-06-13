package ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.onEach
import ui.common.MviViewModel

@Composable
internal fun <Effect> HandleNavigationEffect(
    viewModel: MviViewModel<*, *, Effect>,
    onEffect: (Effect) -> Unit
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.effect.onEach { effect ->
            onEffect(effect)
        }.collect {}
    }
}
