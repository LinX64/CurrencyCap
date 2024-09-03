package ui.screens.main.crypto_list

import androidx.lifecycle.viewModelScope
import data.util.Constant.CRYPTO_LIST_KEY
import domain.repository.MainRepository
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import ui.common.MviViewModel
import ui.screens.main.crypto_list.CryptoListState.Error
import ui.screens.main.crypto_list.CryptoListState.Success
import ui.screens.main.crypto_list.CryptoListViewEvent.OnGetCryptoList
import ui.screens.main.crypto_list.CryptoListViewEvent.OnRetry

class CryptoListViewModel(
    private val mainRepository: MainRepository,
) : MviViewModel<CryptoListViewEvent, CryptoListState, CryptoListNavigationEffect>(CryptoListState.Idle) {

    init {
        handleEvent(OnGetCryptoList)
    }

    override fun handleEvent(event: CryptoListViewEvent) {
        when (event) {
            OnGetCryptoList -> fetchCryptoList()
            OnRetry -> {}
        }
    }

    private fun fetchCryptoList() {
        viewModelScope.launch {
            val store = mainRepository.getAllRatesNew()
            store.stream(StoreReadRequest.cached(CRYPTO_LIST_KEY, false))
                .collect { response ->
                    when (response) {
                        is StoreReadResponse.Loading -> setState { CryptoListState.Loading }
                        is StoreReadResponse.Data -> {
                            val cryptoRates = response.value.crypto
                            if (cryptoRates.isNotEmpty()) {
                                setState { Success(cryptoRates) }
                            } else setState { Error("No data found") }
                        }

                        is StoreReadResponse.Error -> setState {
                            Error(response.errorMessageOrNull() ?: "Error")
                        }

                        else -> Unit
                    }
                }
        }
    }
}