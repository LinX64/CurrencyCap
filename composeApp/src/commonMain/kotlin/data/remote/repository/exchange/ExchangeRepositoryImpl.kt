package data.remote.repository.exchange

import data.util.Constant.ALL_RATES_KEY
import domain.model.CurrencyRate
import domain.model.main.Currencies
import domain.repository.ExchangeRepository
import domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse

class ExchangeRepositoryImpl(
    private val mainRepository: MainRepository
) : ExchangeRepository {

    override fun getLatest(forceRefresh: Boolean): Flow<List<CurrencyRate>> = flow {
        mainRepository.getAllRatesNew()
            .stream(StoreReadRequest.cached(ALL_RATES_KEY, false))
            .map { response ->
                when (response) {
                    is StoreReadResponse.Data -> {
                        val data = response.value
                        emit(mapIRRFiatToToman(data))
                    }

                    is StoreReadResponse.Error -> {
                        emit(emptyList())
                    }

                    else -> Unit
                }
            }
    }

    private fun mapIRRFiatToToman(data: Currencies): List<CurrencyRate> {
        val iranianRealRate = data.bonbast.first { it.code == "USD" }.sell

        return data.rates
            .filter { it.type == FIAT && it.symbol !in unrecognizedSymbols }
            .map { rate ->
                CurrencyRate(
                    code = rate.symbol,
                    value = if (rate.symbol == "IRR") iranianRealRate else rate.rateUsd.toDouble(),
                )
            }
    }

    companion object {
        private const val FIAT = "fiat"
        private val unrecognizedSymbols = setOf(
            "CNH", "XAG", "PEN", "UYU", "AWG", "KYD", "XOF", "XPT",
            "XPF", "XAU", "XDR", "BND", "UGX", "XCD", "BIF", "XPD",
            "SSP", "SZL", "SHP"
        )
    }
}

