package ui.screens.main.search.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.baseline_close_24
import currencycap.composeapp.generated.resources.close
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun TrailingIcon(
    expanded: Boolean,
    onCloseClick: () -> Unit
) {
    if (expanded) {
        IconButton(
            onClick = onCloseClick,
            modifier = Modifier.semantics { traversalIndex = 2f }
        ) {
            Icon(
                painter = painterResource(Res.drawable.baseline_close_24),
                contentDescription = stringResource(Res.string.close)
            )
        }
    }
}
