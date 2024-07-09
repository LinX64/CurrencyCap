package ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ui.screens.initial.privacy_policy.PrivacyPolicySection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PrivacyPolicyBottomSheet(
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    val coroutineScope = rememberCoroutineScope()
    val hideModalBottomSheet: () -> Unit =
        { coroutineScope.launch { scaffoldState.bottomSheetState.hide() } }

    BottomSheetScaffold(
        modifier = modifier.fillMaxWidth(),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        containerColor = MaterialTheme.colorScheme.surface,
        sheetShape = RoundedCornerShape(topStart = 55.dp, topEnd = 55.dp),
        sheetContent = {
            Box {
                BottomSheetHeader(
                    title = "Privacy Policy",
                    onCloseClick = { hideModalBottomSheet() }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .heightIn(max = 700.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    content()
                }
            }
        }) { }

    LaunchedEffect(scaffoldState.bottomSheetState) {
        scope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    }
}

@Composable
private fun BottomSheetHeader(
    title: String,
    onCloseClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = onCloseClick) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PrivacyBottomSheet(sheetState: BottomSheetScaffoldState) {
    PrivacyPolicyBottomSheet(scaffoldState = sheetState) { PrivacyPolicySection() }
}