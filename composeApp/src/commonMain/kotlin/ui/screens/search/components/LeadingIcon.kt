package ui.screens.search.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_search_normal
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun LeadingIcon() {
    Icon(
        painter = painterResource(Res.drawable.ic_search_normal),
        contentDescription = null
    )
}