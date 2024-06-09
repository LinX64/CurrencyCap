package ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.today_top_movers
import org.jetbrains.compose.resources.stringResource
import ui.screens.home.MainState
import util.getIconBy

@Composable
internal fun TodayTopMovers(
    mainState: MainState
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = stringResource(Res.string.today_top_movers),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (mainState is MainState.Success) {
                items(mainState.topMovers.size) {
                    val topMovers = mainState.topMovers[it]

                    TopMoversItem(
                        icon = getIconBy(topMovers.symbol),
                        name = topMovers.symbol
                    )
                }
            }

            if (mainState is MainState.Loading) {
                items(4) {
                    TopMoversItemLoading()
                }
            }
        }
    }
}


