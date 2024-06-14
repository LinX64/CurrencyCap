package ui.screens.search.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.baseline_search_24
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun LeadingIcon() {
    Icon(
        painter = painterResource(Res.drawable.baseline_search_24),
        contentDescription = null
    )
}