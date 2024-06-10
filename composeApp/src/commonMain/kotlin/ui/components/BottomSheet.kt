package ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SubscribeBottomSheet(
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        modifier = modifier.fillMaxWidth(),
        scaffoldState = scaffoldState,
        sheetContent = { content() },
        sheetPeekHeight = 0.dp
    ) {}

    LaunchedEffect(scaffoldState.bottomSheetState) {
        scope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    }
}