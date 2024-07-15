package ui.screens.main.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.no_results_found
import currencycap.composeapp.generated.resources.try_searching_for
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun EmptyView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.no_results_found),
            style = MaterialTheme.typography.titleSmall
        )

        Text(
            text = stringResource(Res.string.try_searching_for),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
