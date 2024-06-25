package ui.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ui.screens.subscribers.SubscribersSection

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
        sheetPeekHeight = 0.dp,
        containerColor = Color.Transparent,
        sheetShape = RoundedCornerShape(topStart = 55.dp, topEnd = 55.dp),
        sheetContent = {
            BoxWithConstraints(
                Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 10.dp)
            ) {
                content()
            }
        }) { }

    LaunchedEffect(scaffoldState.bottomSheetState) {
        scope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomSheet(sheetState: BottomSheetScaffoldState) {
    SubscribeBottomSheet(scaffoldState = sheetState) { SubscribersSection() }
}