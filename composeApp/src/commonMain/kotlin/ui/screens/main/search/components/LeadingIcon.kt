package ui.screens.main.search.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.ic_search_normal
import currencycap.composeapp.generated.resources.search
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LeadingIcon() {
    Icon(
        painter = painterResource(Res.drawable.ic_search_normal),
        contentDescription = stringResource(Res.string.search)
    )
}