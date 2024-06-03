package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import domain.model.RateDao
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.koinViewModel


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
    rates: MainState
) {
    LazyColumn {
        if (rates is MainState.Success) {
            items(rates.rates.size) { index ->
                RateItem(rate = rates.rates[index])
            }
        }
    }

    when (rates) {
        is MainState.Loading -> {
            Text("Loading...")
        }

        is MainState.Error -> {
            Text("Error: ${rates.error}")
        }

        else -> Unit
    }
}

@Composable
fun RateItem(rate: RateDao) {
    Column {
        Text(text = rate.code)
        Text(text = rate.buy.toString())
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