package ui.screens.main.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.HazeState
import di.koinViewModel
import org.koin.core.parameter.parametersOf
import ui.components.ErrorView
import ui.components.base.BaseGlassLazyColumn
import ui.screens.main.detail.DetailViewEvent.OnRetry
import ui.screens.main.detail.components.DescriptionCard
import ui.screens.main.detail.components.DetailBody
import ui.screens.main.detail.components.DetailHeader
import ui.theme.AppDimensions.SPACER_PADDING_16
import util.getDummyCryptoInfo
import util.getDummyCryptoItem

@Composable
internal fun DetailRoute(
    padding: PaddingValues,
    hazeState: HazeState,
    symbol: String,
    detailViewModel: DetailViewModel = koinViewModel { parametersOf(symbol) }
) {
    val state by detailViewModel.viewState.collectAsStateWithLifecycle()
    DetailScreen(
        state = state,
        padding = padding,
        hazeState = hazeState,
        handleEvent = detailViewModel::handleEvent
    )
}

@Composable
internal fun DetailScreen(
    state: DetailState,
    padding: PaddingValues,
    hazeState: HazeState,
    handleEvent: (DetailViewEvent) -> Unit
) {
    BaseGlassLazyColumn(
        padding = padding,
        hazeState = hazeState,
        isEmpty = state is DetailState.Error,
        emptyContent = { ErrorView(onRetry = { handleEvent(OnRetry) }) },
        verticalArrangement = Arrangement.spacedBy(SPACER_PADDING_16)
    ) {
        when (state) {
            is DetailState.Success -> {
                val description = state.cryptoInfo.description.en
                item { DetailHeader(crypto = state.crypto, cryptoInfo = state.cryptoInfo) }
                item { DetailBody(crypto = state.crypto) }
                item { DescriptionCard(description = description) }
            }

            is DetailState.Loading -> {
                val crypto = getDummyCryptoItem()
                val cryptoInfo = getDummyCryptoInfo()
                val description = "state.description"

                item { DetailHeader(crypto, isLoading = true, cryptoInfo = cryptoInfo) }
                item { DetailBody(crypto = crypto, isLoading = true) }
                item { DescriptionCard(description = description, isLoading = true) }
            }

            else -> Unit
        }
    }
}
