package ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import domain.model.RateDao
import koinViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.screens.components.RateItem

@Composable
fun HomeRoute(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>()
) {
    val ratesState = mainViewModel.rates.collectAsState().value
    HomeScreen(
        rates = ratesState
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    rates: MainState
) {
    val hazeState = remember { HazeState() }
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize()
            .haze(
                state = hazeState,
                style = HazeStyle(
                    HazeDefaults.tint(Color.Transparent),
                    HazeDefaults.blurRadius,
                    HazeDefaults.noiseFactor
                )
            ),
        columns = GridCells.Fixed(2),
    ) {
        if (rates is MainState.Success) {
            items(rates.rates.size) { index ->
                RateItem(rate = rates.rates[index])
            }
        }
    }

    when (rates) {
        is MainState.Loading -> {
            CircularProgressIndicator()
        }

        is MainState.Error -> {
            Text("Error: ${rates.error}")
        }

        else -> Unit
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        rates = MainState.Success(
            rates = listOf(
                RateDao("USD", 1, 1),
                RateDao("EUR", 0, 0),
                RateDao("GBP", 0, 0)
            )
        )
    )
}