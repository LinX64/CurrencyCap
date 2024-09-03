package ui.screens.main.crypto_list

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
import org.jetbrains.compose.resources.stringResource
import ui.components.ErrorView
import ui.components.base.BaseGlassLazyColumn
import ui.screens.main.crypto_list.CryptoListState.Error
import ui.screens.main.crypto_list.CryptoListViewEvent.OnRetry
import ui.screens.main.overview.components.CryptoHorizontalItem
import ui.theme.AppDimensions.SPACER_PADDING_8
import util.getDummyCryptoItems

@Composable
internal fun CryptoListRoute(
    cryptoListViewModel: CryptoListViewModel = koinViewModel(),
    hazeState: HazeState,
    onCryptoItemClick: (id: String, symbol: String) -> Unit,
) {
    val state by cryptoListViewModel.viewState.collectAsStateWithLifecycle()
    BaseGlassLazyColumn(
        hazeState = hazeState,
        isEmpty = state is Error,
        emptyContent = {
            ErrorView(
                message = stringResource(Res.string.an_error_occurred),
                onRetry = { cryptoListViewModel.handleEvent(OnRetry) }
            )
        }
    ) {
        when (state) {
            is CryptoListState.Success -> {
                val cryptoRates = (state as CryptoListState.Success).cryptoList
                items(
                    count = cryptoRates.size,
                    key = { index -> cryptoRates[index].id }
                ) { index ->
                    CryptoHorizontalItem(
                        crypto = cryptoRates[index],
                        isLoading = false,
                        onClick = onCryptoItemClick
                    )

                    if (index < cryptoRates.size - 1) {
                        Spacer(modifier = Modifier.height(SPACER_PADDING_8))
                    }
                }
            }

            is CryptoListState.Loading -> {
                items(10) { index ->
                    val dummyCryptoItems = getDummyCryptoItems()
                    CryptoHorizontalItem(
                        crypto = dummyCryptoItems[index],
                        isLoading = true,
                        onClick = onCryptoItemClick
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