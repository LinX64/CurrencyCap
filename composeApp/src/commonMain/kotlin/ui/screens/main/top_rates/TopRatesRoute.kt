package ui.screens.main.top_rates

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import currencycap.composeapp.generated.resources.Res
import currencycap.composeapp.generated.resources.an_error_occurred
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import domain.model.main.BonbastRate
import org.jetbrains.compose.resources.stringResource
import ui.components.ErrorView
import ui.components.base.BaseGlassLazyColumn
import ui.screens.main.overview.components.RateItem
import ui.screens.main.top_rates.TopRatesState.Error
import ui.screens.main.top_rates.TopRatesState.Loading
import ui.screens.main.top_rates.TopRatesState.Success
import ui.screens.main.top_rates.TopRatesViewEvent.OnRetry
import ui.theme.AppDimensions.SPACER_PADDING_8

@Composable
internal fun TopRatesRoute(
    topRatesViewModel: TopRatesViewModel = koinViewModel(),
    hazeState: HazeState,
) {
    val state by topRatesViewModel.viewState.collectAsStateWithLifecycle()
    BaseGlassLazyColumn(
        hazeState = hazeState,
        isEmpty = state is Error,
        emptyContent = {
            ErrorView(
                message = stringResource(Res.string.an_error_occurred),
                onRetry = { topRatesViewModel.handleEvent(OnRetry) }
            )
        }
    ) {
        when (state) {
            is Success -> {
                val topRates = (state as Success).topRates
                items(
                    count = topRates.size,
                    key = { index -> topRates[index].code }
                ) { index ->
                    RateItem(
                        isLoading = false,
                        icon = topRates[index].imageUrl,
                        rate = topRates[index],
                    )

                    if (index < topRates.size - 1) {
                        Spacer(modifier = Modifier.height(SPACER_PADDING_8))
                    }
                }
            }

            is Loading -> {
                items(10) { index ->
                    RateItem(
                        isLoading = true,
                        icon = "",
                        rate = BonbastRate(code = "", sell = 0.0, buy = 0.0, imageUrl = "")
                    )

                    if (index < 4) {
                        Spacer(modifier = Modifier.height(SPACER_PADDING_8))
                    }
                }
            }

            else -> Unit
        }
    }
}