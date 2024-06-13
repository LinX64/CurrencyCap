package ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.onEach
import ui.common.MviViewModel

@Composable
fun <State, Effect> ComposeScreen(
    viewModel: MviViewModel<*, State, Effect>,
    onEffect: (Effect) -> Unit,
    content: @Composable (State) -> Unit
) {
    val state by viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = viewModel) {
        viewModel.effect.onEach { effect ->
            onEffect(effect)
        }.collect {}
    }

    content(state)
}